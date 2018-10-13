use lab5;

DROP TABLE vendorsproducts;
CREATE TABLE vendorsproducts (
	[vendor_product_id] INT IDENTITY(1,1) NOT NULL,
	[vendor_id] INT NOT NULL,
	[product_id] INT NOT NULL,
	CONSTRAINT [FK_ProductsVendors] FOREIGN KEY (vendor_id) REFERENCES [product] (product_id),
	CONSTRAINT [FK_VendorsProducts] FOREIGN KEY (product_id) REFERENCES [vendor] (vendor_id)
) ON [PRIMARY];

ALTER TABLE vendorsproducts ADD PRIMARY KEY ([vendor_product_id]);

ALTER TABLE vendorsproducts ADD PRIMARY KEY ([vendor_product_id]);
CREATE TABLE product_profile (
	[profile_id] INT IDENTITY(1,1) NOT NULL,
	[image] IMAGE DEFAULT NULL,
	[description] TEXT DEFAULT 'No description',
	[container] VARCHAR(50) DEFAULT NULL,
	[price] FLOAT DEFAULT 0.0,
	[container_weight] FLOAT DEFAULT 0.0,
	[expiration_term] FLOAT DEFAULT 0.0,
	[product_id] INT DEFAULT NULL,
	CONSTRAINT CHK_Price CHECK ([price] > 0),
	CONSTRAINT CHK_Expiration CHECK ([expiration_term] < 3)
) ON [PRIMARY];

ALTER TABLE product_profile ADD PRIMARY KEY ([profile_id]);

ALTER TABLE product_profile ADD CONSTRAINT [FK_Product] 
FOREIGN KEY (product_id) REFERENCES [product] (product_id);


CREATE TABLE product (
	[product_id] INT IDENTITY(1,1) NOT NULL,
	[type] VARCHAR(30) NOT NULL,
	[name] VARCHAR(50) NOT NULL,
	[profile_id] INT DEFAULT NULL,
	[vendors] INT DEFAULT NULL
) ON [PRIMARY];

ALTER TABLE product ADD CONSTRAINT [FK_ProductProductprofile] 
FOREIGN KEY (profile_id) REFERENCES [product_profile] (profile_id);

ALTER TABLE product ADD PRIMARY KEY ([product_id]);

CREATE TABLE vendor (
	[vendor_id] INT IDENTITY(1,1) NOT NULL,
	[director_name] VARCHAR(60) DEFAULT 'No name',
	[phone-number] VARCHAR(30) DEFAULT 'No number',
	[address] VARCHAR(60) DEFAULT 'No number',
	[products] INT DEFAULT NULL
) ON [PRIMARY];

ALTER TABLE vendor ADD PRIMARY KEY ([vendor_id]);

CREATE TABLE invoice (
	[invoice_number] INT NOT NULL,
	[date] DATE NOT NULL,
	[vendor_id] INT DEFAULT NULL,
	[product_id] INT DEFAULT NULL,
	CONSTRAINT [FK_InvoiceVendor] FOREIGN KEY (vendor_id) REFERENCES [vendor] (vendor_id),
	CONSTRAINT [FK_InvoiceProduct] FOREIGN KEY (product_id) REFERENCES [product] (product_id)
) ON [PRIMARY];

ALTER TABLE invoice ADD PRIMARY KEY ([invoice_number]);

SET IDENTITY_INSERT product_profile OFF;

INSERT INTO product_profile(
[image],
[description],
[container],
[price],
[container_weight],
[expiration_term],
[product_id]
)
 VALUES(
(SELECT * FROM OPENROWSET(BULK N'C:\Users\gursk\Pictures\Screenshots\qwerty.png', SINGLE_BLOB) IMG_DATA),
'wrehnettgrebren',
 'container', 5.5, 6.4, 2, NULL);
