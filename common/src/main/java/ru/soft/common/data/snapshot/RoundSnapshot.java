package ru.soft.common.data.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.StationsSchema;

@Builder
@JsonRootName("roundSnapshot")
public record RoundSnapshot(
        @JsonProperty("stationsSchema") @Nonnull @NotNull StationsSchema stationsSchema,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("order") @Min(value = 0) @Max(value = 100) int order) {
}

