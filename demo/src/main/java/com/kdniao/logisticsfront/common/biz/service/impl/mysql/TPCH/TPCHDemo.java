package com.kdniao.logisticsfront.common.biz.service.impl.mysql.TPCH;

import com.kdniao.logisticsfront.common.biz.service.impl.mysql.MySQLDemo;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 * partsupp     供货商零件       [ps_partkey, ps_suppkey]
 * part         零件信息        p_partkey
 * supplier     供货商信息       s_suppkey [s_nationkey]
 * <p>
 * lineitem     在线商品信息  l_orderkey, l_linenumber [l_partkey, l_suppkey]
 * <p>
 * orders       订单信息        o_orderkey [o_custkey]
 * customer     消费者信息       c_custkey [c_nationkey]
 * <p>
 * nation       国家信息        n_nationkey [n_regionkey]
 * region       地区信息        r_regionkey
 * <p>
 * 总结:
 * 1.国家属于地区, 供应商和消费者属于国家
 * 2.订单属于消费者
 * 2.供货商零件关联了零件和供货商
 * 2.在线商品信息关联了零件,供货商和订单
 */
public class TPCHDemo {
    /*1.Q1：价格统计报告查询
Q1语句是查询lineItems的一个定价总结报告。在单个表lineitem上查询某个时间段内，对已经付款的、已经运送的等各类商品进行统计，包括业务量的计费、发货、折扣、税、平均价格等信息。
Q1语句的特点是：带有分组、排序、聚集操作并存的单表查询操作。这个查询会导致表上的数据有95%到97%行被读取到。

    EXPLAIN SELECT
        L_RETURNFLAG,
        L_LINESTATUS,
        SUM( L_QUANTITY ) AS SUM_QTY,
        SUM( L_EXTENDEDPRICE ) AS SUM_BASE_PRICE,
        SUM( L_EXTENDEDPRICE * ( 1 - L_DISCOUNT ) ) AS SUM_DISC_PRICE,
        SUM( L_EXTENDEDPRICE * ( 1 - L_DISCOUNT ) * ( 1 + L_TAX ) ) AS SUM_CHARGE,
        AVG( L_QUANTITY ) AS AVG_QTY,
        AVG( L_EXTENDEDPRICE ) AS AVG_PRICE,
        AVG( L_DISCOUNT ) AS AVG_DISC,
        COUNT( * ) AS COUNT_ORDER
    FROM
        lineitem
    WHERE
        L_SHIPDATE <= DATE '1998-12-01' - INTERVAL '90' DAY
    GROUP BY
        L_RETURNFLAG,
        L_LINESTATUS
    ORDER BY
        L_RETURNFLAG,
        L_LINESTATUS;

    * 优化: (待完成 ????????)
    * 1.去除where运算[L_SHIPDATE <= DATE '1998-12-01' - INTERVAL '90' DAY] 改为 [L_SHIPDATE <= DATE '1998-09-02']
    * 2.添加联合索引 [`l_shipDATE`, `l_returnflag`, `l_linestatus`, `l_quantity`, `l_extendedprice`, `l_discount`] 时间降低到10s以内
    * 3.
    * */


    /*
    * 2.Q2: 最小代价供货商查询
Q2语句查询获得最小代价的供货商。得到给定的区域内，对于指定的零件（某一类型和大小的零件），哪个供应者能以最低的价格供应它，就可以选择哪个供应者来订货。
Q2语句的特点是：带有排序、聚集操作、子查询并存的多表查询操作。查询语句没有从语法上限制返回多少条元组，但是TPC-H标准规定，查询结果只返回前100行（通常依赖于应用程序实现）。

    EXPLAIN SELECT
        S_ACCTBAL,
        S_NAME,
        N_NAME,
        P_PARTKEY,
        P_MFGR,
        S_ADDRESS,
        S_PHONE,
        S_COMMENT
    FROM
        part,
        supplier,
        partsupp,
        nation,
        region
    WHERE
        P_PARTKEY = PS_PARTKEY
        AND S_SUPPKEY = PS_SUPPKEY
        AND P_SIZE = 15
        AND P_TYPE LIKE '%BRASS'
        AND S_NATIONKEY = N_NATIONKEY
        AND N_REGIONKEY = R_REGIONKEY
        AND R_NAME = 'EUROPE'
        AND PS_SUPPLYCOST = (
    SELECT
        MIN( PS_SUPPLYCOST )
    FROM
        partsupp,
        supplier,
        nation,
        region
    WHERE
        P_PARTKEY = PS_PARTKEY
        AND S_SUPPKEY = PS_SUPPKEY
        AND S_NATIONKEY = N_NATIONKEY
        AND N_REGIONKEY = R_REGIONKEY
        AND R_NAME = 'EUROPE'
        )
    ORDER BY
        S_ACCTBAL DESC,
        N_NAME,
        S_NAME,
        P_PARTKEY;

    * 优化:
    * 1.添加partsupp表的索引[`ps_partkey`, `ps_suppkey`]
    * 2.添加supplier表的索引[`s_suppkey`, `s_nationkey`, `s_acctbal`, `s_name`]
    * 3.添加part表的索引[`p_size`, `p_partkey`, `p_type`, `p_mfgr`]
    * 4.添加nation表的索引[`n_nationkey`]
    * 5.修改nation表的索引为[`ps_partkey`, `ps_suppkey`, `ps_supplycost`],去除[Using index condition]
* */

    /*3.Q3: 运送优先级查询
Q3语句查询得到收入在前10位的尚未运送的订单。在指定的日期之前还没有运送的订单中具有最大收入的订单的运送优先级（订单按照收入的降序排序）和潜在的收入（潜在的收入为l_extendedprice * (1-l_discount)的和）。

    EXPLAIN SELECT
        L_ORDERKEY,
        SUM( L_EXTENDEDPRICE * ( 1 - L_DISCOUNT ) ) AS REVENUE,
        O_ORDERDATE,
        O_SHIPPRIORITY
    FROM
        customer,
        orders,
        lineitem
    WHERE
        C_MKTSEGMENT = 'BUILDING'
        AND C_CUSTKEY = O_CUSTKEY
        AND L_ORDERKEY = O_ORDERKEY
        AND O_ORDERDATE < DATE '1995-03-15' AND L_SHIPDATE > DATE '1995-03-15'
    GROUP BY
        L_ORDERKEY,
        O_ORDERDATE,
        O_SHIPPRIORITY
    ORDER BY
        REVENUE DESC,
        O_ORDERDATE;

    * 优化: (待完成 ???????? 现在是1秒多)
    * 1.添加orders表的索引[`o_orderkey`, `o_custkey`, `o_orderDATE`]
    * 2.添加customer表的索引[`c_custkey`, `c_mktsegment`]
    * 3.添加lineitem表的索引[`l_orderkey`, `l_shipDATE`]
    * 1.修改orders表的索引[`o_custkey`, `o_orderkey`, `o_shippriority`, `o_orderDATE`]
    * 1.修改customer表的索引[`c_mktsegment`, `c_custkey`]
*/

    /*4.Q4: 订单优先级查询
Q4语句查询得到订单优先级统计值。计算给定的某三个月的订单的数量，在每个订单中至少有一行由顾客在它的提交日期之后收到。
Q4语句的特点是：带有分组、排序、聚集操作、子查询并存的单表查询操作。子查询是相关子查询。

    EXPLAIN SELECT
	O_ORDERPRIORITY,
	COUNT( * ) AS ORDER_COUNT
    FROM
        orders
    WHERE
        O_ORDERDATE >= DATE '1993-07-01'
        AND O_ORDERDATE < DATE '1993-07-01' + INTERVAL '3' MONTH
        AND EXISTS ( SELECT * FROM lineitem WHERE L_ORDERKEY = O_ORDERKEY AND L_COMMITDATE < L_RECEIPTDATE )
    GROUP BY
        O_ORDERPRIORITY
    ORDER BY
        O_ORDERPRIORITY;
    SHOW WARNINGS;

    * 优化: (待完成 ???????? 0.4s)
    * 1.添加orders表的索引[`o_orderDATE`, `o_orderkey`, `o_orderpriority`]
    * 2.添加lineitem表的索引[`l_orderkey`, `l_receiptDATE`, `l_commitDATE`]
    * 3.修改EXISTS为IN子句

*/

    /*5.Q5: 某地区供货商为公司带来的收入查询
Q5语句查询得到通过某个地区零件供货商而获得的收入（收入按sum(l_extendedprice * (1 -l_discount))计算）统计信息。可用于决定在给定的区域是否需要建立一个当地分配中心。
Q5语句的特点是：带有分组、排序、聚集操作、子查询并存的多表连接查询操作。
    EXPLAIN SELECT
	N_NAME,
	SUM( L_EXTENDEDPRICE * ( 1 - L_DISCOUNT ) ) AS REVENUE
    FROM
        customer,
        orders,
        lineitem,
        supplier,
        nation,
        region
    WHERE
        C_CUSTKEY = O_CUSTKEY
        AND L_ORDERKEY = O_ORDERKEY
        AND L_SUPPKEY = S_SUPPKEY
        AND C_NATIONKEY = S_NATIONKEY
        AND S_NATIONKEY = N_NATIONKEY
        AND N_REGIONKEY = R_REGIONKEY
        AND R_NAME = 'ASIA'
        AND O_ORDERDATE >= DATE '1994-01-01'
        AND O_ORDERDATE < DATE '1994-01-01' + INTERVAL '1' YEAR
    GROUP BY
        N_NAME
    ORDER BY
        REVENUE DESC;
    SHOW WARNINGS;

    * 优化: (待完成 ???????? 3秒)
    * 1.添加customer表的索引[`c_nationkey`, `c_custkey`]
    * 2.添加nation表的索引[`n_regionkey`, `n_nationkey`]
    * 3.
*/

    /*6.Q6: 预测收入变化查询
Q6语句查询得到某一年中通过变换折扣带来的增量收入。这是典型的“what-if”判断，用来寻找增加收入的途径。预测收入变化查询考虑了指定的一年中折扣在“DISCOUNT-0.01”和“DISCOUNT＋0.01”之间的已运送的所有订单，求解把l_quantity小于quantity的订单的折扣消除之后总收入增加的数量。
Q6语句的特点是：带有聚集操作的单表查询操作。查询语句使用了BETWEEN-AND操作符，有的数据库可以对BETWEEN-AND进行优化。

*/

    /*7.Q7: 货运盈利情况查询
Q7语句是查询从供货商国家与销售商品的国家之间通过销售获利情况的查询。此查询确定在两国之间货运商品的量用以帮助重新谈判货运合同。

Q7语句的特点是：带有分组、排序、聚集、子查询操作并存的多表查询操作。子查询的父层查询不存在其他查询对象，是格式相对简单的子查询。*/

    /*8.Q8: 国家市场份额查询
Q8语句是查询在过去的两年中一个给定零件类型在某国某地区市场份额的变化情况。

Q8语句的特点是：带有分组、排序、聚集、子查询操作并存的查询操作。子查询的父层查询不存在其他查询对象，是格式相对简单的子查询，但子查询自身是多表连接的查询。*/

    /*9.Q9: 产品类型利润估量查询
Q9语句是查询每个国家每一年所有被定购的零件在一年中的总利润。

Q9语句的特点是：带有分组、排序、聚集、子查询操作并存的查询操作。子查询的父层查询不存在其他查询对象，是格式相对简单的子查询，但子查询自身是多表连接的查询。子查询中使用了LIKE操作符，有的查询优化器不支持对LIKE操作符进行优化。*/

    /*10.Q10: 货运存在问题的查询
Q10语句是查询每个国家在某时刻起的三个月内货运存在问题的客户和造成的损失。

Q10语句的特点是：带有分组、排序、聚集操作并存的多表连接查询操作。查询语句没有从语法上限制返回多少条元组，但是TPC-H标准规定，查询结果只返回前10行（通常依赖于应用程序实现）。*/

    /*11.Q11: 库存价值查询
Q11语句是查询库存中某个国家供应的零件的价值。

Q11语句的特点是：带有分组、排序、聚集、子查询操作并存的多表连接查询操作。子查询位于分组操作的HAVING条件中。*/

    /*12.Q12: 货运模式和订单优先级查询
Q12语句查询获得货运模式和订单优先级。可以帮助决策：选择便宜的货运模式是否会导致消费者更多的在合同日期之后收到货物，而对紧急优先命令产生负面影响。

Q12语句的特点是：带有分组、排序、聚集操作并存的两表连接查询操作。*/

    /*13.Q13: 消费者订单数量查询
Q13语句查询获得消费者的订单数量，包括过去和现在都没有订单记录的消费者。

Q13语句的特点是：带有分组、排序、聚集、子查询、左外连接操作并存的查询操作。*/

    /*14.Q14: 促销效果查询
Q14语句查询获得某一个月的收入中有多大的百分比是来自促销零件。用以监视促销带来的市场反应。

Q14语句的特点是：带有分组、排序、聚集、子查询、左外连接操作并存的查询操作。*/

    /*15.Q15: 头等供货商查询
Q15语句查询获得某段时间内为总收入贡献最多的供货商（排名第一）的信息。可用以决定对哪些头等供货商给予奖励、给予更多订单、给予特别认证、给予鼓舞等激励。

Q15语句的特点是：带有分排序、聚集、聚集子查询操作并存的普通表与视图的连接操作。*/

    /*16.Q16: 零件/供货商关系查询
Q16语句查询获得能够以指定的贡献条件供应零件的供货商数量。可用于决定在订单量大，任务紧急时，是否有充足的供货商。
Q16语句的特点是：带有分组、排序、聚集、去重、NOT IN子查询操作并存的两表连接操作。*/

    /*17.Q17: 小订单收入查询
Q17语句查询获得比平均供货量的百分之二十还低的小批量订单。对于指定品牌和指定包装类型的零件，决定在一个七年数据库的所有订单中这些订单零件的平均项目数量（过去的和未决的）。如果这些零件中少于平均数20％的订单不再被接纳，那平均一年会损失多少呢？所以此查询可用于计算出如果没有没有小量订单，平均年收入将损失多少（因为大量商品的货运，将降低管理费用）。
Q17语句的特点是：带有聚集、聚集子查询操作并存的两表连接操作。*/

    /*18.Q18: 大订单顾客查询
Q18语句查询获得比指定供货量大的供货商信息。可用于决定在订单量大，任务紧急时，验证否有充足的供货商。
Q18语句的特点是：带有分组、排序、聚集、IN子查询操作并存的三表连接操作。查询语句没有从语法上限制返回多少条元组，但是TPC-H标准规定，查询结果只返回前100行（通常依赖于应用程序实现）。*/

    /*19.Q19: 折扣收入查询
Q19语句查询得到对一些空运或人工运输零件三个不同种类的所有订单的总折扣收入。零件的选择考虑特定品牌、包装和尺寸范围。本查询是用数据挖掘工具产生格式化代码的一个例子。
Q19语句的特点是：带有分组、排序、聚集、IN子查询操作并存的三表连接操作。*/

    /*20.Q20: 供货商竞争力查询
Q20语句查询确定在某一年内，找出指定国家的能对某一零件商品提供更有竞争力价格的供货货。所谓更有竞争力的供货商，是指那些零件有过剩的供货商，超过供或商在某一年中货运给定国的某一零件的50％则为过剩。
Q20语句的特点是：带有排序、聚集、IN子查询、普通子查询操作并存的两表连接操作。*/

    /*21.Q21: 不能按时交货供货商查询
Q21语句查询获得不能及时交货的供货商。
Q21语句的特点是：带有分组、排序、聚集、EXISTS子查询、NOT EXISTS子查询操作并存的四表连接操作。查询语句没有从语法上限制返回多少条元组，但是TPC-H标准规定，查询结果只返回前100行（通常依赖于应用程序实现）。*/

    /*22.Q22: 全球销售机会查询
Q22语句查询获得消费者可能购买的地理分布。本查询计算在指定的国家，比平均水平更持肯定态度但还没下七年订单的消费者数量。能反应出普通消费者的的态度，即购买意向。
Q22语句的特点是：带有分组、排序、聚集、EXISTS子查询、NOT EXISTS子查询操作并存的四表连接操作。*/
}
