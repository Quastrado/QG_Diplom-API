INSERT INTO class (
    url, name, english, description, is_hidden_entity, parent_url, hit_dice, caster_type,
    features, saving_throws, primary_characteristics, created_at, updated_at
)
VALUES
    -- Бард и подклассы
    ('bard', 'Бард', 'Bard', 'Вдохновляющий заклинатель, чья музыка творит магию', false, NULL, 8, 'FULL', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('lore', 'Коллегия знаний', 'College of Lore', 'Барды Коллегии знаний собирают тайны и легенды', false, 'bard', 8, 'FULL', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('dance', 'Коллегия танца', 'College of Dance', 'Барды Коллегии танца направляют магию через грациозные движения', false, 'bard', 8, 'FULL', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),

    -- Монах и подклассы
    ('monk', 'Монах', 'Monk', 'Мастер боевых искусств, использующий энергию ци', false, NULL, 8, 'NONE', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('shadow', 'Путь тени', 'Way of Shadow', 'Монахи Пути тени следуют традициям скрытности и уловок', false, 'monk', 8, 'NONE', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('mercy', 'Путь милосердия', 'Way of Mercy', 'Монахи Пути милосердия управляют жизненной силой для исцеления и вреда', false, 'monk', 8, 'NONE', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('astral-self', 'Путь астрального я', 'Way of the Astral Self', 'Монахи, пробуждающие свою истинную астральную сущность', false, 'monk', 8, 'NONE', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),

    -- Плут и подклассы
    ('rogue', 'Плут', 'Rogue', 'Мастер скрытности, уловок и точных смертоносных ударов', false, NULL, 8, 'NONE', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('assassin', 'Ликвидатор', 'Assassin', 'Плут, специализирующийся на устранении целей и маскировке', false, 'rogue', 8, 'NONE', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('soulknife', 'Клинок души', 'Soulknife', 'Плут, поражающий врагов псионическими клинками разума', false, 'rogue', 8, 'NONE', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),

    -- Колдун и подклассы
    ('warlock', 'Колдун', 'Warlock', 'Заклинатель, черпающий магию из сделки с могущественным покровителем', false, NULL, 8, 'PACT', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('great-old-one', 'Великий Древний', 'The Great Old One', 'Покровитель из непостижимых глубин Дальнего Предела', false, 'warlock', 8, 'PACT', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW()),
    ('archfey', 'Архифея', 'The Archfey', 'Покровитель из загадочного и капризного Царства Фей', false, 'warlock', 8, 'PACT', '[]'::jsonb, '[]'::jsonb, '[]'::jsonb, NOW(), NOW())
ON CONFLICT (url) DO UPDATE SET
    name = EXCLUDED.name,
    english = EXCLUDED.english,
    description = EXCLUDED.description,
    is_hidden_entity = EXCLUDED.is_hidden_entity,
    parent_url = EXCLUDED.parent_url,
    hit_dice = EXCLUDED.hit_dice,
    caster_type = EXCLUDED.caster_type,
    features = EXCLUDED.features,
    saving_throws = EXCLUDED.saving_throws,
    primary_characteristics = EXCLUDED.primary_characteristics,
    updated_at = NOW();
