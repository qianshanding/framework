package com.qianshanding.framework.commons.page;

import java.io.Serializable;
import java.util.List;

/**
 * 该类主要是提供分页类,是一个<code>final</code>类.防止继承了该类而修改不必要的属性.<br/>
 * <br/>
 * 
 * 使用方法:<br/>
 * 
 * <br/>
 * 1、该类创建后必须设置当前页,默认为第一页,如:<br/>
 * <code>setCurPage(2)</code>设置为当前显示第二页;<br/>
 * 
 * 2、可以根据自己喜好设置每页多少条记录显示,如:<br/>
 * <code>setPageSize(15)</code>设置 为每页显示15条记录;<br/>
 * 
 * 3、可以通过<br/>
 * <code>getItemList()</code>获取当前查询到的数据.其中<code>T</code>是自身定义的类型;<br/>
 * 
 * 查询数据用法:<br/>
 * 
 * 1.在要进来查询的时候首先查询到数据的总记录.<br/>
 * 
 * 2.设置到总记录后.<code>setTotalRecord(总记录数)</code><br/>
 * 
 * 3.获取<code>getStartRow()</code>开始记录,获取<code>getEndRow()</code>结束记录<br/>
 * 
 * 4.通过3里面的开始记录和结束记录查询数据信息
 * 
 * @author Fish
 * 
 * @param <T>
 */
public final class Page<T> implements Serializable {
	private static final long serialVersionUID = -4443715053463068200L;
	/**
	 * 总记录
	 */
	private Integer totalRecord;
	/**
	 * 每页记录,默认每页10条记录.
	 */
	private Integer sizePage = 10;
	/**
	 * 当前页,从第一页开始
	 */
	private Integer curPage = 1;
	/**
	 * 总页数
	 */
	private Integer totalPages;
	/**
	 * 起始行数
	 */
	private Integer startRow;
	/**
	 * 结束行数
	 */
	private Integer endRow;
	/**
	 * 返回的查询数据信息
	 */
	private List<T> itemList;
	/**
	 * 排序字段
	 */
	private String orderBy;
	/**
	 * 排序desc, asc
	 */
	private String order;

	/**
	 * 默认构造函数
	 */
	public Page() {
		this(10);
	}

	/**
	 * 带参数构造函数
	 * 
	 * @param sizePage
	 */
	public Page(int sizePage) {
		super();
		this.sizePage = sizePage;
	}

	/**
	 * 返回查询数据的总记录
	 */
	public int getTotalRecord() {
		return totalRecord == null ? 0 : totalRecord;
	}

	/**
	 * 设置查询总记录
	 */
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	/**
	 * 返回每页记录,如果没有设置,则默认为每页10条记录
	 */
	public int getSizePage() {
		return sizePage;
	}

	/**
	 * 设置每页的记录.
	 */
	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}

	/**
	 * 当前页数,如果没有设置则是从第一页开始.
	 */
	public int getCurPage() {
		return curPage;
	}

	/**
	 * 设置当前页数,页面设置的值如果少于或者等于0时候则赋值 1.
	 */
	public void setCurPage(int curPage) {
		this.curPage = ((curPage <= 0) ? 1 : curPage);
	}

	/**
	 * 根据页数和每页设置数量获取数据库查询记录开始
	 */
	public int getStartRow() {
		startRow = ((getCurPage() - 1) * getSizePage());
		return startRow;
	}

	/**
	 * 根据页数和每页设置数量获取数据查询记录结束
	 */
	public int getEndRow() {
		int curRow = getStartRow() + getSizePage();
		endRow = curRow > getTotalRecord() ? getTotalRecord() : curRow;
		return endRow;
	}

	/**
	 * 可以根据查询设置<code>setItemList()</code>方法获取数据.
	 */
	public List<T> getItemList() {
		return itemList;
	}

	/**
	 * 可以在dal里面查询设置到该对象集合里面.
	 * 
	 * @param itemList
	 */
	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	/**
	 * 判断是否有上页,如果存在上页返回<code>true</code>,否则反之.
	 */
	public boolean getPrePage() {
		return (getCurPage() != 1);
	}

	/**
	 * 判断是否有上页,如果存在下页返回<code>true</code>,否则反之.
	 */
	public boolean getNextPage() {
		return curPage < getTotalPages();
	}

	/**
	 * 获取总页数
	 */
	public int getTotalPages() {
		totalPages = ((getTotalRecord() % getSizePage() == 0) ? getTotalRecord() / getSizePage()
				: (getTotalRecord() / getSizePage()) + 1);
		return totalPages;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
