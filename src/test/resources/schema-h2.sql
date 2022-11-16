CREATE TABLE IF NOT EXISTS "user" (
	"id" int8 NOT NULL,
	"cellphone" varchar(255) NULL,
	"email" varchar(255) NULL,
	"firstname" varchar(255) NULL,
	"lastname" varchar(255) NULL,
	"username" varchar(255) NULL,
	CONSTRAINT user_pkey PRIMARY KEY ("id")
);