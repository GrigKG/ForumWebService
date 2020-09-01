package telran.ashkelon2020.forum.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatePeriodDTO {
	LocalDate dateFrom;
	LocalDate dateTo;
}
