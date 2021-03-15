## 基本概念：

* Broker : 和AMQP里协议的概念一样， 就是消息中间件所在的服务器
* Topic(主题) : 每条发布到Kafka集群的消息都有一个类别，这个类别被称为Topic。（物理上不同Topic的消息分开存储，逻辑上一个Topic的消息虽然保存于一个或多个broker上但用户只需指定消息的Topic即可生产或消费数据而不必关心数据存于何处）
* Partition(分区) : Partition是物理上的概念，体现在磁盘上面，每个Topic包含一个或多个Partition.
* Producer : 负责发布消息到Kafka broker
* Consumer : 消息消费者，向Kafka broker读取消息的客户端。
* Consumer Group（消费者群组） : 每个Consumer属于一个特定的Consumer Group（可为每个Consumer指定group name，若不指定group name则属于默认的group）。
* offset 偏移量： 是kafka用来确定消息是否被消费过的标识，在kafka内部体现就是一个递增的数字
  
> kafka消息发送的时候 ,考虑到性能 可以采用打包方式发送， 也就是说 传统的消息是一条一条发送， 现在可以先把需要发送的消息缓存在客户端， 等到达一定数值时， 再一起打包发送， 而且还可以对发送的数据进行压缩处理，减少在数据传输时的开销

### server 参数解释：
* log.dirs: 日志文件存储地址， 可以设置多个
* num.recovery.threads.per.data.dir：用来读取日志文件的线程数量，对应每一个log.dirs 若此参数为2 log.dirs 为 2个目录 那么就会有4个线程来读取
* auto.create.topics.enable:是否自动创建tiopic
* num.partitions: 创建topic的时候自动创建多少个分区 (可以在创建topic的时候手动指定)
* log.retention.hours: 日志文件保留时间 超时即删除
* log.retention.bytes: 日志文件最大大小
* log.segment.bytes: 当日志文件达到一定大小时，开辟新的文件来存储(分片存储)
* log.segment.ms: 同上 只是当达到一定时间时 开辟新的文件
* message.max.bytes: broker能接收的最大消息大小(单条) 默认1M

### kafka基本管理操作命令
* 列出所有主题 kafka-topics.bat --zookeeper localhost:2181/kafka --list
* 列出所有主题的详细信息 kafka-topics.bat --zookeeper localhost:2181/kafka --describe
* 创建主题 主题名 my-topic，1副本，8分区 kafka-topics.bat --zookeeper localhost:2181/kafka --create --replication-factor 1 --partitions 8 --topic my-topic
* 增加分区，注意：分区无法被删除 kafka-topics.bat --zookeeper localhost:2181/kafka --alter --topic my-topic --partitions 16
* 删除主题 kafka-topics.bat --zookeeper localhost:2181/kafka --delete --topic my-topic* 列出消费者群组（仅Linux） kafka-topics.sh --new-consumer --bootstrap-server localhost:9092/kafka --list
* 列出消费者群组详细信息（仅Linux） kafka-topics.sh --new-consumer --bootstrap-server localhost:9092/kafka --describe --group 群组名

## kafka生产者参数详解

### acks:
至少要多少个分区副本接收到了消息返回确认消息 一般是 0:只要消息发送出去了就确认(不管是否失败) 1:只要 有
一个broker接收到了消息 就返回 all： 所有集群副本都接收到了消息确认 当然 2 3 4 5 这种数字都可以， 就是具体
多少台机器接收到了消息返回， 但是一般这种情况很少用到
### buffer.memory：
生产者缓存在本地的消息大小 ： 如果生产者在生产消息的速度过快 快过了往 broker发送消息的速度 那么就会出
现buffer.memory不足的问题 默认值为32M 注意 单位是byte 大概3355000左右
### max.block.ms:
生产者获取kafka元数据(集群数据，服务器数据等) 等待时间 ： 当因网络原因导致客户端与服务器通讯时等待的时
间超过此值时 会抛出一个TimeOutExctption 默认值为 60000ms
### retries：
设置生产者生产消息失败后重试的次数 默认值 3次
### retry.backoff.ms:
设置生产者每次重试的间隔 默认 100ms
### batch.size:
生产者批次发送消息的大小 默认16k 注意单位还是byte
### linger.ms:
生产者生产消息后等待多少毫秒发送到broker 与batch.size 谁先到达就根据谁 默认值为0
### compression.type：
kafka在压缩数据时使用的压缩算法 可选参数有:none、gzip、snappy none即不压缩 gzip,和snappy压缩算法之间
取舍的话 gzip压缩率比较高 系统cpu占用比较大 但是带来的好处是 网络带宽占用少， snappy压缩比没有gzip高
cpu占用率不是很高 性能也还行， 如果网络带宽比较紧张的话 可以选择gzip 一般推荐snappy
### client.id:
一个标识， 可以用来标识消息来自哪， 不影响kafka消息生产
### max.in.flight.requests.per.connection：
指定kafka一次发送请求在得到服务器回应之前,可发送的消息数量


## 消费者参数
### fetch.min.bytes：
该属性指定了消费者从服务器获取记录的最小字节数。broker 在收到消费者的数据请求时，如果可用的数据量小
于 fetch.min.bytes 指定的大小，那么它会等到有足够的可用数据时才把它返回给消费者。这样可以降低消费者和
broker 的工作负载，因为它们在主题不是很活跃的时候（或者一天里的低谷时段）就不需要来来回回地处理消
息。如果没有很多可用数据，但消费者的 CPU 使用率却很高，那么就需要把该属性的值设得比默认值大。如果消
费者的数量比较多，把该属性的值设置得大一点可以降低 broker 的工作负载。 默认值为1 byte
### fetch.max.wait.ms
我们通过 fetch.min.bytes 告诉 Kafka，等到有足够的数据时才把它返回给消费者。而 feth.max.wait.ms 则用于指
定 broker 的等待时间，默认是如果没有足够的数据流入Kafka，消费者获取最小数据量的要求就得不到满足，最终
导致 500ms 的延迟。如果 fetch.max.wait.ms 被设为 100ms，并且 fetch.min.bytes 被设为 1MB，那么 Kafka 在
收到消费者的请求后，要么返回 1MB 数据，要么在 100ms 后返回所有可用的数据，就看哪个条件先得到满足。
默认值为500ms
### max.partition.fetch.bytes
该属性指定了服务器从每个分区里返回给消费者的最大字节数。默认值是 1MB
### session.timeout.ms :
消费者多久没有发送心跳给服务器服务器则认为消费者死亡/退出消费者组 默认值:10000ms
### heartbeat.interval.ms :
消费者往kafka服务器发送心跳的间隔 一般设置为session.timeout.ms的三分之一 默认值:3000ms
### auto.offset.reset:
当消费者本地没有对应分区的offset时 会根据此参数做不同的处理 默认值为:latest
earliest
当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
latest
当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
none
topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
### enable.auto.commit
该属性指定了消费者是否自动提交偏移量，默认值是 true。为了尽量避免出现重复数据和数据丢失，可以把它设为
false，由自己控制何时提交偏移量。如果把它设为 true，还可以通过配置 auto.commit.interval.ms 属性来控制
提交的频率。
### partition.assignment.strategy
PartitionAssignor 根据给定的消费者和主题，决定哪些分区应该被分配给哪个消费者。Kafka 有两个默认的分配策
略。
Range：该策略会把主题的若干个连续的分区分配给消费者。假设消费者 C1 和消费者 C2 同时订阅了主题 T1
和主题 T2，并且每个主题有 3 个分区。那么消费者 C1 有可能分配到这两个主题的分区 0 和分区 1，而消费
者 C2 分配到这两个主题的分区2。因为每个主题拥有奇数个分区，而分配是在主题内独立完成的，第一个消
费者最后分配到比第二个消费者更多的分区。只要使用了 Range 策略，而且分区数量无法被消费者数量整
除，就会出现这种情况。
RoundRobin：该策略把主题的所有分区逐个分配给消费者。如果使用 RoundRobin 策略来给消费者 C1 和消
费者 C2 分配分区，那么消费者 C1 将分到主题 T1 的分区 0 和分区 2 以及主题 T2 的分区 1，消费者 C2 将分
配到主题 T1 的分区 1 以及主题 T2 的分区 0 和分区 2。一般来说，如果所有消费者都订阅相同的主题（这种
情况很常见），RoundRobin 策略会给所有消费者分配相同数量的分区（或最多就差一个分区）。
### max.poll.records
单次调用 poll() 方法最多能够返回的记录条数 ,默认值 500
### receive.buffer.bytes 和 send.buffer.bytes
receive.buffer.bytes 默认值 64k 单位 bytes
send.buffer.bytes 默认值 128k 单位 bytes
这两个参数分别指定了 TCP socket 接收和发送数据包的缓冲区大小。如果它们被设为 -1

## 偏移量与偏移量提交
偏移量是一个自增长的ID 用来标识当前分区的哪些消息被消费过了， 这个ID会保存在kafka的broker当中 而且 消费者本地也会存储一份 因为每次消费每一条消息都要更新一下偏移量的话 难免会影响整个broker的吞吐量 
所以一般 这个偏移量在每次发生改动时 先由消费者本地改动， 默认情况下 消费者每五秒钟会提交一次改动的偏移量，默认情况下可能会出现重复消费的问题: 因为可能在下一次提交偏移量之前 消费者本地消费了一些消息，然后发生了分区再均衡 
那么就会出现一个问题 假设上次提交的偏移量是2000 在下一次提交之前 其实消费者又消费了500条数据 也就是说当前的偏移量应该是2500 但是这个2500只在消费者本地， 也就是说 假设其他消费者去消费这个分区的时候拿到的偏移量是2000 那么又会从2000开始消费消息 那么 2000到2500之间的消息又会被消费一遍,这就是重复消费的问题.
解决方案:手动提交 :  关闭默认的自动提交(enable.auto.commit= false) 然后使用kafka提供的API来进行偏移量提交(同步提交&异步提交)
同步提交偏移量会等待服务器应答 并且遇到错误会尝试重试，影响性能
异步提交的对于性能肯定是有提升 但是遇到错误没办法重试 因为可能在收到你这个结果的时候又提交过偏移量了 如果这时候重试 又会导致消息重复的问题了
解决方案:解决方案:手动提交 : 异步提交的话 如果出现问题但是不是致命问题的话 可能下一次提交就不会出现这个问题了， 所以 有些异常
是不需要解决的 所以 我们平时可以采用异步提交的方式 等到消费者中断了(异常/消费再均衡，或是强制中断消费者) 的时候再使用同步提交(因为这次如果失败了就没有下次了... 所以要让他重试)

## Rebalance 分区再均衡, 重新分配分区所有权
在以下情况下会触发Rebalance 操作:
1. 组成员发生变更(新consumer加入组、已有consumer主动离开组或已有consumer崩溃了)
2. 订阅主题数发生变更，如果你使用了正则表达式的方式进行订阅，那么新建匹配正则表达式的topic就会触发rebalance
3. 订阅主题的分区数发生变更

当触发Rebalance时 由于kafka正在分配所有权 会导致消费者不能消费， 而且 还会引发一个重复消费的问题， 当消费者还没来得及提交偏移量时 分区所有权遭到了重新分配 那么这时候就会导致一个消息被多个消费者重复消费
解决方案就是 在消费者订阅时， 添加一个再均衡监听器， 也就是当kafka在做Rebalance 操作前后 均会调用再均衡监听器 那么这时候 我们可以在kafka Rebalance之前提交我们消费者最后处理的消息来解决这个问题。

