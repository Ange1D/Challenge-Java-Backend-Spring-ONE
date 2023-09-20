CREATE TABLE respuestas (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  mensaje VARCHAR(255) NOT NULL,
  topico_id BIGINT NOT NULL,
  fecha_creacion DATETIME NOT NULL,
  autor_id BIGINT NOT NULL,
  solucion BOOLEAN NOT NULL,
  FOREIGN KEY (topico_id) REFERENCES topicos (id),
  FOREIGN KEY (autor_id) REFERENCES usuarios (id)
);