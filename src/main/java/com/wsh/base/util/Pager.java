package com.wsh.base.util;

import java.io.Serializable;
/**
 * 分页工具类
 * @author Wally
 *
 */
public class Pager implements Serializable
{
    private static final long serialVersionUID = 5523540943779813401L;

    /**
     * 页码
     */
    private int pageIndex = 1;

    /**
     * 每页记录数
     */
    private int pageCount = 0;

    /**
     * 总页数
     */
    private int totalPage = 1;

    /**
     * 总记录数
     */
    private int totalRecordCount = 0;
    
    
    private int nextPage=1;
    private int prePage=1;
    
    
    private Object body;

    public int getPageIndex()
    {
        return pageIndex;
    }

    public void setPageIndex( int pageIndex )
    {
        this.pageIndex = pageIndex;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount( int pageCount )
    {
        this.pageCount = pageCount;
    }

    public int getTotalPage()
    {
        return totalPage;
    }

    public void setTotalPage( int totalPage )
    {
        this.totalPage = totalPage;
    }

    public int getTotalRecordCount()
    {
        return totalRecordCount;
    }

    public void setTotalRecordCount( int totalRecordCount )
    {
        this.totalRecordCount = totalRecordCount;
    }

    
    public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	// 每页容量 页码必须大于0,否则抛出IllegalArgumentException
    public static Pager getPager( int totalRecordCount, int pageCount,
            int pageIndex )
    {
        if ( pageCount <= 0 )
        {
            throw new IllegalArgumentException(
                    "pageCount can't be less than zero" );
        }
        int totalPage = totalRecordCount / pageCount
                + (totalRecordCount % pageCount > 0 ? 1 : 0);
        totalPage = Math.max( 1, totalPage );
        pageIndex = Math.max( 1, Math.min( pageIndex, totalPage ) );
        
        Pager pager = new Pager();
        pager.setPageCount( pageCount );
        pager.setPageIndex( pageIndex );
        pager.setTotalPage( totalPage );
        
        if(pageIndex+1<totalPage){
        	pager.nextPage=pageIndex+1;
        }else{
        	pager.nextPage=totalPage;
        }
        
        if(pageIndex<=1){
        	pager.prePage=1;
        }else{
        	pager.prePage=pageIndex-1;
        }
        
        
        pager.setTotalRecordCount( totalRecordCount );
        return pager;
    }

	public int getNextPage() {
		return nextPage;
	}


	public int getPrePage() {
		return prePage;
	}
    
}
