package ru.clevertec.ecl.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import ru.clevertec.ecl.dto.GiftCertificateFilter;

import java.util.Objects;

@UtilityClass
public class SortingUtil {

    public static Sort getSort(GiftCertificateFilter filter) {
        String sortName = filter.getSortName();
        String sortDate = filter.getSortDate();
        Sort sort = null;
        if (sortName != null) {
            sort = Sort.by("name");
            if (Objects.equals("ASC", sortName)) {
                sort = sort.ascending();
            }
            if (Objects.equals("DESC", sortName)) {
                sort = sort.descending();
            }
        }

        if (sortDate != null) {
            Sort sortByDate = Sort.by("createDate");
            if (Objects.equals("ASC", sortDate)) {
                sortByDate = sortByDate.ascending();
            }
            if (Objects.equals("DESC", sortDate)) {
                sortByDate = sortByDate.descending();
            }

            if (sort != null) {
                sort = sort.and(sortByDate);
            } else {
                sort = sortByDate;
            }
        }
        return sort;
    }

}
