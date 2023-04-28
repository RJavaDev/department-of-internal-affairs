package uz.internal_affairs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserScoreDto {
    private Integer userScoreDay = 0;
    private Integer userScoreMonth = 0;

    public void setUserScoreDay(Integer userScoreDay) {
        this.userScoreDay = userScoreDay != null ? userScoreDay : 0;
    }

    public void setUserScoreMonth(Integer userScoreMonth) {
        this.userScoreMonth = userScoreMonth != null ? userScoreMonth : 0;
    }
}
