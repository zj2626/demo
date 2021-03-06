package hello.database.mapper;

import hello.database.model.UCAreaDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
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