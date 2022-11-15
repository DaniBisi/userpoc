CREATE TABLE "user" (
	"id" int8 NOT NULL,
	"cellPhone" varchar(255) NULL,
	"email" varchar(255) NULL,
	"firstName" varchar(255) NULL,
	"lastName" varchar(255) NULL,
	"userName" varchar(255) NULL,
	CONSTRAINT user_pkey PRIMARY KEY ("id")
);