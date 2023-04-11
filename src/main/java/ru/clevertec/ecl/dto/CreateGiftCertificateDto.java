package ru.clevertec.ecl.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.json.deserializer.DurationDeserializer;
import ru.clevertec.ecl.json.serializer.DurationSerializer;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateGiftCertificateDto {

    private String name;
    private String description;
    private BigDecimal price;

    @JsonSerialize(using = DurationSerializer.class)
    @JsonDeserialize(using = DurationDeserializer.class)
    private Duration duration;
    private List<String> tags;

}
