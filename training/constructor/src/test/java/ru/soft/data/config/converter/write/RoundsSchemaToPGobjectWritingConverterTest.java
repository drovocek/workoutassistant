package ru.soft.data.config.converter.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.soft.common.data.RoundsSchema;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.roundsSchema;

class RoundsSchemaToPGobjectWritingConverterTest
        extends BaseEntityToPGobjectWritingConverterTest<RoundsSchema, RoundSchemaToPGobjectWritingConverter> {

    @Override
    protected RoundSchemaToPGobjectWritingConverter writingConverter(ObjectMapper mapper) {
        return new RoundSchemaToPGobjectWritingConverter(mapper);
    }

    @Override
    protected RoundsSchema forWriting() {
        return roundsSchema();
    }
}