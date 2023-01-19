package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.soft.common.data.HasDescription;
import ru.soft.common.data.HasOrder;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rest.class, name = "rest"),
        @JsonSubTypes.Type(value = Station.class, name = "station")
})
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class WorkoutElement implements HasOrder, HasDescription {

    @Nonnull
    @NotBlank
    @JsonProperty("title")
    protected final String title;

    @JsonProperty("note")
    protected final String note;
}
