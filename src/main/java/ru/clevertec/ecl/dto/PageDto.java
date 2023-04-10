package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    private int number;
    private int size;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private int numberOfElements;
    private boolean last;
    private List<T> content;

    public static class Builder<T> {

        private int number;
        private int size;
        private int totalPages;
        private long totalElements;
        private boolean first;
        private int numberOfElements;
        private boolean last;
        private List<T> content;

        private Builder() {
        }

        public static <E> Builder<E> createBuilder(Class<E> eClass) {
            return new Builder<>();
        }

        public Builder<T> setNumber(int number) {
            this.number = number;
            return this;
        }

        public Builder<T> setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder<T> setTotalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> setTotalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> setFirst(boolean first) {
            this.first = first;
            return this;
        }

        public Builder<T> setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
            return this;
        }

        public Builder<T> setLast(boolean last) {
            this.last = last;
            return this;
        }

        public Builder<T> setContent(List<T> content) {
            this.content = content;
            return this;
        }

        public PageDto<T> build() {
            return new PageDto<>(this.number, this.size, this.totalPages, this.totalElements, this.first,
                    this.numberOfElements, this.last, this.content);
        }
    }

}
