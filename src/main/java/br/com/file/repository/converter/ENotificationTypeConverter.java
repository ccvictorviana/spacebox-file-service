package br.com.file.repository.converter;

import br.com.file.domain.enums.ENotificationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ENotificationTypeConverter implements AttributeConverter<ENotificationType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ENotificationType attribute) {
        return attribute.getType();
    }

    @Override
    public ENotificationType convertToEntityAttribute(Integer dbData) {
        return ENotificationType.toEnum(dbData);
    }
}
