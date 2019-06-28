package hello.data.mapper;

import hello.data.model.UCAreaDO;
import org.springframework.data.repository.query.Param;
//import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UCAreaDOMapper {

    Integer insertUCArea(UCAreaDO entity);

    Integer insertBatch(@Param("list") List<UCAreaDO> list);

    Integer updateUCArea(UCAreaDO entity);

    Integer updateBatch(@Param("list") List<UCAreaDO> list);

    Integer deleteUCArea(UCAreaDO entity);

    Integer deleteBatch(@Param("list") List<UCAreaDO> list);

    UCAreaDO getUCArea(UCAreaDO entity);

    List<UCAreaDO> findByEntity(UCAreaDO entity);
}