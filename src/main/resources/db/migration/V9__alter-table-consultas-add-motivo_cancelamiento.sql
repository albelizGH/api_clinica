-- Agregar la columna motivo_cancelamiento con valores posibles en ENUM
ALTER TABLE consultas ADD COLUMN motivo_cancelamiento ENUM('PACIENTE_DESISTIO', 'MEDICO_CANCELO', 'OTROS');

-- Actualizar la tabla para establecer motivo_cancelamiento a NULL (opcional)
UPDATE consultas SET motivo_cancelamiento = NULL;
