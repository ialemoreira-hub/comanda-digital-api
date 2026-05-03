CREATE TABLE pedido_compra (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               fornecedor_id BIGINT,
                               usuario_id BIGINT,
                               status ENUM('RASCUNHO','ENVIADO','RECEBIDO','CANCELADO') DEFAULT 'RASCUNHO',
                               valor_total DECIMAL(10,2) DEFAULT 0,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id),
                               FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

CREATE TABLE pedido_compra_item (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    pedido_compra_id BIGINT NOT NULL,
                                    ingrediente_id BIGINT NOT NULL,
                                    quantidade DECIMAL(10,3) NOT NULL,
                                    preco_unitario DECIMAL(10,4),
                                    subtotal DECIMAL(10,4),
                                    FOREIGN KEY (pedido_compra_id) REFERENCES pedido_compra(id),
                                    FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id)
);