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

-- Novas categorias e pratos
INSERT INTO categoria (nome, descricao, ordem, status) VALUES
    ('Lanches', 'Hambúrgueres e sanduíches', 5, 'ATIVO');

INSERT INTO prato (categoria_id, nome, descricao, preco_venda, tempo_preparo_min, status) VALUES
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Entradas'), 'Bruschetta', 'Bruschetta com queijo e tomate seco', 24.90, 10, 'ATIVO'),
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Pratos Principais'), 'Salmão ao Maracujá', 'Salmão grelhado com molho de maracujá', 69.90, 30, 'ATIVO'),
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Pratos Principais'), 'Lasanha', 'Lasanha à bolonhesa gratinada', 52.90, 40, 'ATIVO'),
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Bebidas'), 'Limonada Suíça', 'Limonada cremosa com leite condensado', 16.90, 10, 'ATIVO'),
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Bebidas'), 'Suco Natural 500ml', 'Laranja, maracujá ou abacaxi - feito na hora', 14.90, 10, 'ATIVO'),
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Bebidas'), 'Refrigerante 350ml', 'Coca-Cola, Guaraná ou Soda', 7.90, 1, 'ATIVO'),
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Lanches'), 'Hambúrguer Artesanal', 'Blend bovino, queijo cheddar e alface', 39.90, 20, 'ATIVO'),
                                                                                              ((SELECT id FROM categoria WHERE nome = 'Lanches'), 'Chicken Burger', 'Frango grelhado, queijo e alface', 34.90, 20, 'ATIVO');

-- Fichas técnicas usando subqueries
-- Bruschetta
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Toste o pão, adicione tomate picado, manjericão e azeite.' FROM prato WHERE nome = 'Bruschetta';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING007'), 2.000, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Bruschetta';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING002'), 0.100, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Bruschetta';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING008'), 5.000, 'G', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Bruschetta';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING009'), 20.000, 'ML', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Bruschetta';

-- Frango Grelhado
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Tempere o frango e grelhe em fogo médio.' FROM prato WHERE nome = 'Frango Grelhado';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING003'), 0.300, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Frango Grelhado';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING010'), 5.000, 'G', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Frango Grelhado';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING009'), 15.000, 'ML', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Frango Grelhado';

-- Picanha na Brasa
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Tempere a picanha e asse na brasa. Sirva com arroz e farofa.' FROM prato WHERE nome = 'Picanha na Brasa';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING004'), 0.350, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Picanha na Brasa';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING011'), 0.100, 'KG', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Picanha na Brasa';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING012'), 0.050, 'KG', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Picanha na Brasa';

-- Pudim
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Misture os ingredientes e leve ao forno em banho-maria.' FROM prato WHERE nome = 'Pudim';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING005'), 1.000, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Pudim';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING006'), 0.200, 'L', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Pudim';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING014'), 3.000, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Pudim';

-- Hambúrguer Artesanal
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Monte o hambúrguer com pão, carne, queijo, alface e tomate.' FROM prato WHERE nome = 'Hambúrguer Artesanal';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING021'), 1.000, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Hambúrguer Artesanal';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING020'), 0.180, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Hambúrguer Artesanal';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING022'), 30.000, 'G', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Hambúrguer Artesanal';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING023'), 20.000, 'G', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Hambúrguer Artesanal';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING002'), 0.050, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Hambúrguer Artesanal';

-- Chicken Burger
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Monte o hambúrguer de frango com pão, queijo e alface.' FROM prato WHERE nome = 'Chicken Burger';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING021'), 1.000, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Chicken Burger';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING024'), 0.180, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Chicken Burger';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING022'), 30.000, 'G', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Chicken Burger';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING023'), 20.000, 'G', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Chicken Burger';

-- Limonada Suíça
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Bata o limão com leite condensado e água com gás.' FROM prato WHERE nome = 'Limonada Suíça';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING025'), 3.000, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Limonada Suíça';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING005'), 0.100, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Limonada Suíça';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING026'), 200.000, 'ML', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Limonada Suíça';

-- Suco Natural 500ml
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Bata a fruta com água e coe.' FROM prato WHERE nome = 'Suco Natural 500ml';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING027'), 500.000, 'ML', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Suco Natural 500ml';

-- Refrigerante 350ml
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Sirva gelado.' FROM prato WHERE nome = 'Refrigerante 350ml';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING028'), 350.000, 'ML', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Refrigerante 350ml';

-- Salmão ao Maracujá
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Grelhe o salmão e sirva com molho de maracujá.' FROM prato WHERE nome = 'Salmão ao Maracujá';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING015'), 0.250, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Salmão ao Maracujá';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING016'), 2.000, 'UN', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Salmão ao Maracujá';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING009'), 15.000, 'ML', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Salmão ao Maracujá';

-- Lasanha
INSERT INTO ficha_tecnica (prato_id, rendimento, modo_preparo)
SELECT id, 1, 'Monte a lasanha em camadas e asse no forno.' FROM prato WHERE nome = 'Lasanha';

INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING017'), 0.200, 'KG', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Lasanha';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING020'), 0.200, 'KG', 1.10 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Lasanha';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING018'), 0.200, 'KG', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Lasanha';
INSERT INTO ficha_tecnica_item (ficha_tecnica_id, ingrediente_id, quantidade, unidade, fator_correcao)
SELECT ft.id, (SELECT id FROM ingrediente WHERE sku = 'ING019'), 0.150, 'KG', 1.00 FROM ficha_tecnica ft JOIN prato p ON ft.prato_id = p.id WHERE p.nome = 'Lasanha';

-- Estoque inicial
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 10.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING001';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 5.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING002';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 8.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING003';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 6.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING004';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 10.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING005';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 5.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING006';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 20.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING007';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 200.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING008';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 500.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING009';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 200.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING010';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 10.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING011';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 5.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING012';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 5.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING013';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 30.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING014';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 3.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING015';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 10.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING016';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 3.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING017';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 3.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING018';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 2.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING019';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 3.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING020';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 30.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING021';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 500.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING022';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 500.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING023';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 3.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING024';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 20.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING025';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 2000.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING026';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 2000.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING027';
INSERT INTO estoque_movimentacao (ingrediente_id, tipo, quantidade, motivo, custo_unitario, usuario_id)
SELECT id, 'ENTRADA', 2000.000, 'COMPRA', custo_unitario, (SELECT id FROM usuario WHERE email = 'admin@email.com') FROM ingrediente WHERE sku = 'ING028';