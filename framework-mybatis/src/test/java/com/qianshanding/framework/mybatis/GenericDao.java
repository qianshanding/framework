package com.qianshanding.framework.mybatis;

import com.qianshanding.framework.commons.page.Page;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * DAO层泛型接口，定义基本的DAO功能
 * 
 * @author Fish
 * 
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {
	/**
	 * @Title: insert
	 * @Description:插入一个实体（在数据库INSERT一条记录）
	 * @param entity
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int insert(T entity);

	/**
	 * @Title: update
	 * @Description:修改一个实体对象（UPDATE一条记录）
	 * @param entity
	 *            修改的对象个数，正常情况=1
	 * @return
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int update(T entity);

	/**
	 * @Title: update
	 * @Description: 修改符合条件的记录
	 *               <p>
	 *               此方法特别适合于一次性把多条记录的某些字段值设置为新值（定值）的情况，比如修改符合条件的记录的状态字段
	 *               </p>
	 *               <p>
	 *               此方法的另一个用途是把一条记录的个别字段的值修改为新值（定值），此时要把条件设置为该记录的主键
	 *               </p>
	 * @param paramMap
	 *            用于产生SQL的参数值，包括WHERE条件、目标字段和新值等
	 * @return 修改的记录个数，用于判断修改是否成功
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int update(Map<String, Object> paramMap);

	/**
	 * @Title: delete
	 * @Description:按主键删除记录
	 * @param primaryKey
	 *            主键对象
	 * @return 删除的对象个数，正常情况=1
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int delete(PK primaryKey);

	/**
	 * @Title: delete
	 * @Description: 删除符合条件的记录
	 *               <p>
	 *               <strong>此方法一定要慎用，如果条件设置不当，可能会删除有用的记录！</strong>
	 *               </p>
	 * @param paramMap
	 *            用于产生SQL的参数值，包括WHERE条件（其他参数内容不起作用）
	 * @return
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int delete(Map<String, Object> paramMap);

	/**
	 * @Title: truncate
	 * @Description: 清空表，比delete具有更高的效率，而且是从数据库中物理删除（delete是逻辑删除，被删除的记录依然占有空间）
	 *               <p>
	 *               <strong>此方法一定要慎用！</strong>
	 *               </p>
	 * @return
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int truncate();

	/**
	 * @Title: count
	 * @Description:查询整表总记录数
	 * @return 整表总记录数
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int count();

	/**
	 * @Title: count
	 * @Description:查询符合条件的记录数
	 * @param paramMap
	 *            查询条件参数，包括WHERE条件（其他参数内容不起作用）。此参数设置为null，则相当于count()
	 * @return
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int count(Map<String, Object> paramMap);

	/**
	 * @Title: get
	 * @Description:
	 * @param primaryKey
	 *            主键值
	 * @return 记录实体对象，如果没有符合主键条件的记录，则返回null
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public T get(PK primaryKey);

	/**
	 * @Title: load
	 * @Description:按主键取记录
	 * @param primaryKey
	 *            主键值
	 * @return
	 * @throws 记录实体对象，如果没有符合主键条件的记录，则
	 *             throw DataAccessException
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public T load(PK primaryKey) throws DataAccessException;

	/**
	 * @Title: select
	 * @Description:取全部记录
	 * @return 全部记录实体对象的List
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public List<T> select();

	/**
	 * @Title: select
	 * @Description:按条件查询记录
	 * @param paramMap
	 *            查询条件参数，包括WHERE条件、分页条件、排序条件
	 * @return 符合条件记录的实体对象的List
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public List<T> select(Map<String, Object> paramMap);

	/**
	 * @Title: selectPagination
	 * @Description: 按条件查询记录，并处理成分页结果
	 * @param paramMap
	 *            查询条件参数，包括WHERE条件、分页条件、排序条件
	 * @return Page对象，包括（符合条件的）总记录数、页实体对象List等
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public Page<T> selectPagination(Map<String, Object> paramMap);

	/**
	 * @Title: selectFk
	 * @Description: 按条件查询记录，并把有外键关联的字段的关联对象也处理出来
	 *               <p>
	 *               此方法是为了避免1+N而设置的
	 *               </p>
	 * @param paramMap
	 *            查询条件参数，包括WHERE条件、分页条件、排序条件<strong>（不包括外键关联表条件）</strong>
	 * @return 符合条件记录的实体对象的List
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public List<T> selectFk(Map<String, Object> paramMap);

	/**
	 * 按条件查询记录，并把有外键关联的字段的关联对象也处理出来，并处理成分页结果
	 * <p>
	 * 此方法是为了避免1+N而设置的
	 * </p>
	 * 
	 * @param param
	 *            查询条件参数，包括WHERE条件、分页条件、排序条件<strong>（不包括外键关联表条件）</strong>
	 * @return Page对象，包括（符合条件的）总记录数、页实体对象List等
	 */
	public Page<T> selectFkPagination(Map<String, Object> paramMap);

	/**
	 * @Title: batchInsert
	 * @Description:批量插入
	 * @param list
	 * @return
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int batchInsert(final List<T> list);

	/**
	 * @Title: batchUpdate
	 * @Description:批量修改
	 * @param list
	 * @return
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int batchUpdate(final List<T> list);

	/**
	 * @Title: batchDelete
	 * @Description:批量删除
	 * @param list
	 * @return
	 * @author Fish
	 * @date 2016年3月5日
	 */
	public int batchDelete(final List<PK> list);
}
