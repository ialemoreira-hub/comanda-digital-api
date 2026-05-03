-- Novos ingredientes
INSERT INTO ingrediente (nome, sku, unidade_padrao, estoque_minimo, custo_unitario, status) VALUES
                                                                                                ('Pão Italiano', 'ING007', 'UN', 5.000, 2.0000, 'ATIVO'),
                                                                                                ('Manjericão', 'ING008', 'G', 50.000, 0.1000, 'ATIVO'),
                                                                                                ('Azeite', 'ING009', 'ML', 200.000, 0.0500, 'ATIVO'),
                                                                                                ('Alho', 'ING010', 'G', 100.000, 0.0200, 'ATIVO'),
                                                                                                ('Arroz', 'ING011', 'KG', 2.000, 3.5000, 'ATIVO'),
                                                                                                ('Farofa', 'ING012', 'KG', 1.000, 4.0000, 'ATIVO'),
                                                                                                ('Açúcar', 'ING013', 'KG', 1.000, 3.0000, 'ATIVO'),
                                                                                                ('Ovo', 'ING014', 'UN', 12.000, 0.8000, 'ATIVO'),
                                                                                                ('Salmão', 'ING015', 'KG', 2.000, 60.0000, 'ATIVO'),
                                                                                                ('Maracujá', 'ING016', 'UN', 5.000, 1.5000, 'ATIVO'),
                                                                                                ('Massa de Lasanha', 'ING017', 'KG', 1.000, 8.0000, 'ATIVO'),
                                                                                                ('Molho de Tomate', 'ING018', 'KG', 1.000, 5.0000, 'ATIVO'),
                                                                                                ('Queijo Mussarela', 'ING019', 'KG', 1.000, 35.0000, 'ATIVO'),
                                                                                                ('Carne Moída', 'ING020', 'KG', 1.000, 25.0000, 'ATIVO'),
                                                                                                ('Pão de Hambúrguer', 'ING021', 'UN', 10.000, 1.5000, 'ATIVO'),
                                                                                                ('Queijo Cheddar', 'ING022', 'G', 200.000, 0.1500, 'ATIVO'),
                                                                                                ('Alface', 'ING023', 'G', 100.000, 0.0200, 'ATIVO'),
                                                                                                ('Frango Hambúrguer', 'ING024', 'KG', 1.000, 18.0000, 'ATIVO'),
                                                                                                ('Limão', 'ING025', 'UN', 5.000, 0.5000, 'ATIVO'),
                                                                                                ('Água com Gás', 'ING026', 'ML', 500.000, 0.0100, 'ATIVO'),
                                                                                                ('Suco de Fruta', 'ING027', 'ML', 500.000, 0.0150, 'ATIVO'),
                                                                                                ('Refrigerante', 'ING028', 'ML', 500.000, 0.0080, 'ATIVO');

-- Fichas técnicas dos pratos originais
-- Prato 1: Bruschetta
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (1, 1, 'Toste o pão, adicione tomate picado, manjericão e azeite.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (1, 7, 2.000, 'UN', 1.00),   -- Pão Italiano
                                                                                                           (1, 2, 0.100, 'KG', 1.10),   -- Tomate
                                                                                                           (1, 8, 5.000, 'G', 1.00),    -- Manjericão
                                                                                                           (1, 9, 20.000, 'ML', 1.00);  -- Azeite

-- Prato 2: Frango Grelhado
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (2, 1, 'Tempere o frango e grelhe em fogo médio.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (2, 3, 0.300, 'KG', 1.10),   -- Frango
                                                                                                           (2, 10, 5.000, 'G', 1.00),   -- Alho
                                                                                                           (2, 9, 15.000, 'ML', 1.00);  -- Azeite

-- Prato 3: Picanha na Brasa
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (3, 1, 'Tempere a picanha e asse na brasa. Sirva com arroz e farofa.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (3, 4, 0.350, 'KG', 1.10),   -- Picanha
                                                                                                           (3, 11, 0.100, 'KG', 1.00),  -- Arroz
                                                                                                           (3, 12, 0.050, 'KG', 1.00);  -- Farofa

-- Prato 4: Pudim
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (4, 1, 'Misture os ingredientes e leve ao forno em banho-maria.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (4, 5, 1.000, 'UN', 1.00),   -- Leite Condensado
                                                                                                           (4, 6, 0.200, 'L', 1.00),    -- Creme de Leite
                                                                                                           (4, 14, 3.000, 'UN', 1.00);  -- Ovo

-- Prato 12: Hambúrguer Artesanal
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (12, 1, 'Monte o hambúrguer com pão, carne, queijo, alface e tomate.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (5, 21, 1.000, 'UN', 1.00),  -- Pão de Hambúrguer
                                                                                                           (5, 20, 0.180, 'KG', 1.10),  -- Carne Moída
                                                                                                           (5, 22, 30.000, 'G', 1.00),  -- Queijo Cheddar
                                                                                                           (5, 23, 20.000, 'G', 1.00),  -- Alface
                                                                                                           (5, 2, 0.050, 'KG', 1.10);   -- Tomate

-- Prato 13: Chicken Burger
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (13, 1, 'Monte o hambúrguer de frango com pão, queijo e alface.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (6, 21, 1.000, 'UN', 1.00),  -- Pão de Hambúrguer
                                                                                                           (6, 24, 0.180, 'KG', 1.10),  -- Frango Hambúrguer
                                                                                                           (6, 22, 30.000, 'G', 1.00),  -- Queijo Cheddar
                                                                                                           (6, 23, 20.000, 'G', 1.00);  -- Alface

-- Prato 14: Limonada Suíça
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (14, 1, 'Bata o limão com leite condensado e água com gás.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (7, 25, 3.000, 'UN', 1.00),  -- Limão
                                                                                                           (7, 5, 0.100, 'UN', 1.00),   -- Leite Condensado
                                                                                                           (7, 26, 200.000, 'ML', 1.00); -- Água com Gás

-- Prato 15: Suco Natural 500ml
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (15, 1, 'Bata a fruta com água e coe.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
    (8, 27, 500.000, 'ML', 1.00); -- Suco de Fruta

-- Prato 16: Refrigerante 350ml
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (16, 1, 'Sirva gelado.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
    (9, 28, 350.000, 'ML', 1.00); -- Refrigerante

-- Prato 18: Salmão ao Maracujá
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (18, 1, 'Grelhe o salmão e sirva com molho de maracujá.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (10, 15, 0.250, 'KG', 1.10),  -- Salmão
                                                                                                           (10, 16, 2.000, 'UN', 1.00),  -- Maracujá
                                                                                                           (10, 9, 15.000, 'ML', 1.00);  -- Azeite

-- Prato 19: Lasanha
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo) VALUES (19, 1, 'Monte a lasanha em camadas e asse no forno.');
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao) VALUES
                                                                                                           (11, 17, 0.200, 'KG', 1.00),  -- Massa de Lasanha
                                                                                                           (11, 20, 0.200, 'KG', 1.10),  -- Carne Moída
                                                                                                           (11, 18, 0.200, 'KG', 1.00),  -- Molho de Tomate
                                                                                                           (11, 19, 0.150, 'KG', 1.00);  -- Queijo Mussarela

-- Estoque inicial para todos os ingredientes
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id) VALUES
                                                                                                            (1, 'ENTRADA', 10.000, 'COMPRA', 4.5000, 1),
                                                                                                            (2, 'ENTRADA', 5.000, 'COMPRA', 6.0000, 1),
                                                                                                            (3, 'ENTRADA', 8.000, 'COMPRA', 15.0000, 1),
                                                                                                            (4, 'ENTRADA', 6.000, 'COMPRA', 80.0000, 1),
                                                                                                            (5, 'ENTRADA', 10.000, 'COMPRA', 5.5000, 1),
                                                                                                            (6, 'ENTRADA', 5.000, 'COMPRA', 4.0000, 1),
                                                                                                            (7, 'ENTRADA', 20.000, 'COMPRA', 2.0000, 1),
                                                                                                            (8, 'ENTRADA', 200.000, 'COMPRA', 0.1000, 1),
                                                                                                            (9, 'ENTRADA', 500.000, 'COMPRA', 0.0500, 1),
                                                                                                            (10, 'ENTRADA', 200.000, 'COMPRA', 0.0200, 1),
                                                                                                            (11, 'ENTRADA', 10.000, 'COMPRA', 3.5000, 1),
                                                                                                            (12, 'ENTRADA', 5.000, 'COMPRA', 4.0000, 1),
                                                                                                            (13, 'ENTRADA', 5.000, 'COMPRA', 3.0000, 1),
                                                                                                            (14, 'ENTRADA', 30.000, 'COMPRA', 0.8000, 1),
                                                                                                            (15, 'ENTRADA', 3.000, 'COMPRA', 60.0000, 1),
                                                                                                            (16, 'ENTRADA', 10.000, 'COMPRA', 1.5000, 1),
                                                                                                            (17, 'ENTRADA', 3.000, 'COMPRA', 8.0000, 1),
                                                                                                            (18, 'ENTRADA', 3.000, 'COMPRA', 5.0000, 1),
                                                                                                            (19, 'ENTRADA', 2.000, 'COMPRA', 35.0000, 1),
                                                                                                            (20, 'ENTRADA', 3.000, 'COMPRA', 25.0000, 1),
                                                                                                            (21, 'ENTRADA', 30.000, 'COMPRA', 1.5000, 1),
                                                                                                            (22, 'ENTRADA', 500.000, 'COMPRA', 0.1500, 1),
                                                                                                            (23, 'ENTRADA', 500.000, 'COMPRA', 0.0200, 1),
                                                                                                            (24, 'ENTRADA', 3.000, 'COMPRA', 18.0000, 1),
                                                                                                            (25, 'ENTRADA', 20.000, 'COMPRA', 0.5000, 1),
                                                                                                            (26, 'ENTRADA', 2000.000, 'COMPRA', 0.0100, 1),
                                                                                                            (27, 'ENTRADA', 2000.000, 'COMPRA', 0.0150, 1),
                                                                                                            (28, 'ENTRADA', 2000.000, 'COMPRA', 0.0080, 1);