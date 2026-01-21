CREATE TABLE goals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL,

    category VARCHAR(50) NOT NULL,
    difficulty VARCHAR(20) NOT NULL,

    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    daily_minimum_effort INT NOT NULL,

    visibility VARCHAR(20) NOT NULL DEFAULT 'PRIVATE',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    created_at DATETIME NOT NULL,

    CONSTRAINT fk_goals_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE RESTRICT,

    CONSTRAINT chk_daily_minimum_effort
        CHECK (daily_minimum_effort > 0),

    CONSTRAINT chk_goal_dates
        CHECK (start_date <= end_date)
) ENGINE=InnoDB;
