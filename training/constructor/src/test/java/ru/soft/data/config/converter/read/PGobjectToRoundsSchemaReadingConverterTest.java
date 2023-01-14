package ru.soft.data.config.converter.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.RoundsSchema;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.roundsSchema;

class PGobjectToRoundsSchemaReadingConverterTest extends BasePGobjectToEntityReadingConverterTest<RoundsSchema, PGobjectToRoundSchemaReadingConverter> {

    @Override
    protected PGobjectToRoundSchemaReadingConverter readingConverter(ObjectMapper mapper) {
        return new PGobjectToRoundSchemaReadingConverter(mapper);
    }

    @Override
    protected RoundsSchema expected() {
        return roundsSchema();
    }
}