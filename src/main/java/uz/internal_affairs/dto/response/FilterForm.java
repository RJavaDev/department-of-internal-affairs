package uz.internal_affairs.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FilterForm implements Serializable {
    private static final long serialVersionUID = -1183975305038088044L;

    private Integer start = 0;

    private Integer length = 10;

    private Map<String, Object> filter;
}
