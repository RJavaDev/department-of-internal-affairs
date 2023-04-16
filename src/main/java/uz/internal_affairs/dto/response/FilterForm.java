package uz.internal_affairs.dto.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FilterForm implements Serializable {
    private static final long serialVersionUID = -1183975305038088044L;

    private Integer start;

    private Integer length;

    private List<OrderForm> order;

    private Map<String, String> filter;
}
