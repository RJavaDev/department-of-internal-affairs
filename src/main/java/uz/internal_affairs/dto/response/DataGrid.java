package uz.internal_affairs.dto.response;

import uz.internal_affairs.dto.citizen_cotegory.BaseCitizenDto;

import java.util.List;

public class DataGrid<T> {
    private int total;
    private List<T> rows;

    public int getTotal(){
        return total;
    }

    public void setTotal(int total){
        this.total = total;
    }

    public List<T> getRows(){
        return rows;
    }

    public void setRows(List<T> rows){
        this.rows = rows;
    }
    public void setRowsCitizen(List<? extends BaseCitizenDto> rowsCitizen){
    }

//    public void setRows(List<? extends BaseCitizenDto> categoryDateRegionFilter) {
//    }
}
