-- Admin padrão (senha: senha123 em BCrypt)
INSERT INTO usuario (nome, email, senha_hash, perfil, status) VALUES
('Admin', 'admin@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'ADMIN', 'ATIVO');

-- Categorias
INSERT INTO categoria (nome, descricao, ordem, status) VALUES
('Entradas', 'Petiscos e entradas', 1, 'ATIVO'),
('Pratos Principais', 'Pratos principais do cardápio', 2, 'ATIVO'),
('Sobremesas', 'Doces e sobremesas', 3, 'ATIVO'),
('Bebidas', 'Bebidas diversas', 4, 'ATIVO');

-- Ingredientes
INSERT INTO ingrediente (nome, sku, unidade_padrao, estoque_minimo, custo_unitario, status) VALUES
('Farinha de Trigo', 'ING001', 'KG', 2.000, 4.5000, 'ATIVO'),
('Tomate', 'ING002', 'KG', 1.000, 6.0000, 'ATIVO'),
('Frango', 'ING003', 'KG', 2.000, 15.0000, 'ATIVO'),
('Picanha', 'ING004', 'KG', 2.000, 80.0000, 'ATIVO'),
('Leite Condensado', 'ING005', 'UN', 3.000, 5.5000, 'ATIVO'),
('Creme de Leite', 'ING006', 'L', 3.000, 4.0000, 'ATIVO');

-- Pratos
INSERT INTO prato (categoria_id, nome, descricao, preco_venda, tempo_preparo_min, status) VALUES
(1, 'Bruschetta', 'Pão tostado com tomate e manjericão', 18.90, 10, 'ATIVO'),
(2, 'Frango Grelhado', 'Frango grelhado com legumes', 42.90, 25, 'ATIVO'),
(2, 'Picanha na Brasa', 'Picanha com arroz e farofa', 89.90, 35, 'ATIVO'),
(3, 'Pudim', 'Pudim de leite condensado', 16.90, 5, 'ATIVO');

-- Fornecedor
INSERT INTO fornecedor (razao_social, cnpj, telefone, email, status) VALUES
('Distribuidora Alimentos SA', '12.345.678/0001-90', '(11) 3333-4444', 'contato@distribuidora.com', 'ATIVO');