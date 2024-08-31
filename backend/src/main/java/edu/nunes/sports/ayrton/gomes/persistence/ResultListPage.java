package edu.nunes.sports.ayrton.gomes.persistence;

import java.util.List;

public class ResultListPage <T>{

    private long itemCount;
    private int pageIndex;
    private int pageSize;

    private List<T> items;

    public ResultListPage(Long itemCount, int pageIndex, int pageSize, List<T> items) {
        this.itemCount = itemCount;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.items = items;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long intemCount) {
        this.itemCount = intemCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
