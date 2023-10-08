drop table if exists application cascade;


drop table if exists admission_info cascade;


drop table if exists admission_status cascade;


drop table if exists ged_admission_grade cascade;


drop table if exists graduate_admission_grade cascade;


drop table if exists admission_grade cascade;


drop table if exists identities cascade;


drop table if exists middle_school_grade cascade;


drop table if exists users cascade;


CREATE TABLE admission_grade
(
    dtype              VARCHAR(31)   NOT NULL,
    admission_grade_id BIGINT        NOT NULL auto_increment,
    percentile_rank    DECIMAL(5, 3) NOT NULL,
    total_score        DECIMAL(6, 3) NOT NULL,
    primary key (admission_grade_id)
);


create table admission_info
(
    admission_info_id       bigint       not null,
    address                 varchar(255) not null,
    applicant_birth         date         not null,
    applicant_gender        varchar(255) not null,
    applicant_image_uri     varchar(255) not null,
    applicant_name          varchar(255) not null,
    applicant_phone_number  varchar(255) not null,
    first_desired_major     varchar(255) not null,
    second_desired_major    varchar(255) not null,
    third_desired_major     varchar(255) not null,
    detail_address          varchar(255) not null,
    graduation              varchar(255) not null,
    guardian_name           varchar(255) not null,
    guardian_phone_number   varchar(255) not null,
    relation_with_applicant varchar(255) not null,
    school_location         varchar(255),
    school_name             varchar(255),
    screening               varchar(255) not null,
    teacher_name            varchar(255),
    teacher_phone_number    varchar(255),
    telephone               varchar(255),
    primary key (admission_info_id)
);


create table admission_status
(
    application_status_id          bigint       not null,
    final_major                    varchar(255),
    first_evaluation               varchar(255) not null,
    is_final_submitted             boolean      not null,
    is_prints_arrived              boolean      not null,
    registration_number            bigint,
    screening_first_evaluation_at  varchar(255),
    screening_second_evaluation_at varchar(255),
    second_evaluation              varchar(255) not null,
    second_score                   numeric(38, 2),
    primary key (application_status_id)
);


create table application
(
    application_id                             bigint not null auto_increment,
    user_id                                    bigint not null,
    admission_grade_admission_grade_id         bigint not null,
    admission_info_admission_info_id           bigint not null,
    admission_status_application_status_id     bigint not null,
    middle_school_grade_middle_school_grade_id bigint not null,
    primary key (application_id)
);


CREATE TABLE ged_admission_grade
(
    ged_max_score      DECIMAL(38, 2) NOT NULL,
    ged_total_score    DECIMAL(38, 2) NOT NULL,
    admission_grade_id BIGINT         NOT NULL,
    PRIMARY KEY (admission_grade_id)
);

CREATE TABLE graduate_admission_grade
(
    artistic_score                 DECIMAL(5, 3) NOT NULL,
    attendance_score               DECIMAL(5, 3) NOT NULL,
    curricular_subtotal_score      DECIMAL(6, 3) NOT NULL,
    extracurricular_subtotal_score DECIMAL(5, 3) NOT NULL,
    grade_1_semester_1_score       DECIMAL(5, 3) NOT NULL,
    grade_1_semester_2_score       DECIMAL(5, 3) NOT NULL,
    grade_2_semester_1_score       DECIMAL(5, 3) NOT NULL,
    grade_2_semester_2_score       DECIMAL(5, 3) NOT NULL,
    grade_3_semester_1_score       DECIMAL(5, 3) NOT NULL,
    volunteer_score                DECIMAL(5, 3) NOT NULL,
    admission_grade_id             BIGINT        NOT NULL,
    PRIMARY KEY (admission_grade_id)
);


create table identities
(
    identity_id  bigint not null auto_increment,
    birth        date,
    gender       varchar(255),
    name         varchar(255),
    phone_number varchar(255),
    user_id      bigint,
    primary key (identity_id)
);


CREATE TABLE middle_school_grade
(
    middle_school_grade_id   BIGINT NOT NULL,
    middle_school_grade_text TEXT   NOT NULL,
    PRIMARY KEY (middle_school_grade_id)
);


create table users
(
    user_id     bigint not null auto_increment,
    provider    varchar(255),
    provider_id varchar(255),
    role        varchar(255),
    primary key (user_id)
);

ALTER TABLE application
    ADD CONSTRAINT UK_5heq93474271p9oo8kw241xin UNIQUE (admission_grade_admission_grade_id);

ALTER TABLE application
    ADD CONSTRAINT UK_1xj1p12axsq5gn93ehqi3lhkd UNIQUE (admission_info_admission_info_id);

ALTER TABLE application
    ADD CONSTRAINT UK_1ud1g0ic8yy4oejuwsnb1fsl UNIQUE (admission_status_application_status_id);

ALTER TABLE application
    ADD CONSTRAINT UK_aai80sffoev9prh4sobp1dsv0 UNIQUE (middle_school_grade_middle_school_grade_id);

ALTER TABLE identities
    ADD CONSTRAINT UK_gi5aydpad5j0hnvjrfv1716rv UNIQUE (user_id);

ALTER TABLE application
    ADD CONSTRAINT fk_application_admission_grade
        FOREIGN KEY (admission_grade_admission_grade_id)
            REFERENCES admission_grade (admission_grade_id)
            ON DELETE CASCADE;

ALTER TABLE application
    ADD CONSTRAINT fk_application_admission_info
        FOREIGN KEY (admission_info_admission_info_id)
            REFERENCES admission_info (admission_info_id)
            ON DELETE CASCADE;

ALTER TABLE application
    ADD CONSTRAINT fk_application_admission_status
        FOREIGN KEY (admission_status_application_status_id)
            REFERENCES admission_status (application_status_id)
            ON DELETE CASCADE;

ALTER TABLE application
    ADD CONSTRAINT fk_application_middle_school_grade
        FOREIGN KEY (middle_school_grade_middle_school_grade_id)
            REFERENCES middle_school_grade (middle_school_grade_id)
            ON DELETE CASCADE;

ALTER TABLE ged_admission_grade
    ADD CONSTRAINT fk_ged_admission_grade
        FOREIGN KEY (admission_grade_id)
            REFERENCES admission_grade (admission_grade_id)
            ON DELETE CASCADE;

ALTER TABLE graduate_admission_grade
    ADD CONSTRAINT fk_graduate_admission_grade
        FOREIGN KEY (admission_grade_id)
            REFERENCES admission_grade (admission_grade_id)
            ON DELETE CASCADE;