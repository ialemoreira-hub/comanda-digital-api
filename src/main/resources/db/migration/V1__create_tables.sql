CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    perfil ENUM('ADMIN','GERENTE','COZINHEIRO','CLIENTE') NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    status ENUM('ATIVO','INATIVO') DEFAULT 'ATIVO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    ordem INT DEFAULT 0,
    status ENUM('ATIVO','INATIVO') DEFAULT 'ATIVO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE prato (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoria_id BIGINT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    foto_url VARCHAR(255),
    preco_venda DECIMAL(10,2) NOT NULL,
    tempo_preparo_min INT,
    status ENUM('ATIVO','INATIVO','PAUSADO') DEFAULT 'ATIVO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

CREATE TABLE ingrediente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sku VARCHAR(50) UNIQUE,
    unidade_padrao ENUM('G','ML','UN','KG','L') NOT NULL,
    estoque_minimo DECIMAL(10,3) DEFAULT 0,
    custo_unitario DECIMAL(10,4) DEFAULT 0,
    status ENUM('ATIVO','INATIVO') DEFAULT 'ATIVO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ficha_tecnica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    prato_id BIGINT NOT NULL UNIQUE,
    rendimento INT DEFAULT 1,
    modo_preparo TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (prato_id) REFERENCES prato(id)
);

CREATE TABLE ficha_tecnica_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ficha_tecnica_id BIGINT NOT NULL,
    ingrediente_id BIGINT NOT NULL,
    quantidade DECIMAL(10,3) NOT NULL,
    unidade VARCHAR(10),
    fator_correcao DECIMAL(5,2) DEFAULT 1.00,
    FOREIGN KEY (ficha_tecnica_id) REFERENCES ficha_tecnica(id),
    FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id)
);

CREATE TABLE fornecedor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    razao_social VARCHAR(150) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100),
    status ENUM('ATIVO','INATIVO') DEFAULT 'ATIVO',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE fornecedor_produto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fornecedor_id BIGINT NOT NULL,
    ingrediente_id BIGINT NOT NULL,
    preco DECIMAL(10,4) NOT NULL,
    unidade_venda VARCHAR(10),
    UNIQUE(fornecedor_id, ingrediente_id),
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id),
    FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id)
);

CREATE TABLE pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    status ENUM('RECEBIDO','CONFIRMADO','EM_PREPARO','PRONTO','SAIU_ENTREGA','FINALIZADO','CANCELADO') DEFAULT 'RECEBIDO',
    valor_total DECIMAL(10,2),
    endereco_entrega VARCHAR(255),
    observacoes TEXT,
    motivo_cancelamento TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES usuario(id)
);

CREATE TABLE pedido_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    prato_id BIGINT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    observacoes TEXT,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (prato_id) REFERENCES prato(id)
);

CREATE TABLE estoque_movimentacao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ingrediente_id BIGINT NOT NULL,
    tipo ENUM('ENTRADA','SAIDA','ESTORNO') NOT NULL,
    quantidade DECIMAL(10,3) NOT NULL,
    motivo ENUM('COMPRA','VENDA','DESPERDICIO','VENCIMENTO','AJUSTE','ESTORNO') NOT NULL,
    lote VARCHAR(50),
    validade DATE,
    custo_unitario DECIMAL(10,4),
    pedido_id BIGINT,
    usuario_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ingrediente_id) REFERENCES ingrediente(id),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);