-- Create the petshop database
CREATE DATABASE IF NOT EXISTS petshop;

-- Switch to the petshop database
USE petshop;

-- Table to store information about pet categories
CREATE TABLE pet_categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

-- Table to store information about pets
CREATE TABLE pets (
    pet_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    breed VARCHAR(50) NOT NULL,
    age INT,
    price DECIMAL(10, 2),
    category_id INT,
    description TEXT,
    image_url VARCHAR(255),
    FOREIGN KEY (category_id) REFERENCES pet_categories(category_id)
);

-- Table to store information about grooming services
CREATE TABLE grooming_services (
    service_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    available BOOLEAN DEFAULT true
);

-- Table to associate pets with specific grooming services 
CREATE TABLE pet_grooming_relationship (
    pet_id INT,
    service_id INT,
    PRIMARY KEY (pet_id, service_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    FOREIGN KEY (service_id) REFERENCES grooming_services(service_id)
);

-- Table to store information about vaccinations
CREATE TABLE vaccinations (
    vaccination_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    available BOOLEAN DEFAULT true
);

-- Table to associate pets with specific vaccinations 
CREATE TABLE pet_vaccination_relationship (
    pet_id INT,
    vaccination_id INT,
    PRIMARY KEY (pet_id, vaccination_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    FOREIGN KEY (vaccination_id) REFERENCES vaccinations(vaccination_id)
);

-- Table to store information about addresses
CREATE TABLE addresses (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(50),
    zip_code VARCHAR(20)
);

-- Table to store information about customers
CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    phone_number VARCHAR(20),
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

-- Table to store information about transactions
CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    pet_id INT,
    transaction_date DATE,
    amount DECIMAL(10, 2),
    transaction_status ENUM('Success', 'Failed'),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
);

-- Table to store information about pet food
CREATE TABLE pet_food (
    food_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(100),
    type VARCHAR(50),
    quantity INT,
    price DECIMAL(10, 2)
);

-- Table to associate pets with specific food 
CREATE TABLE pet_food_relationship (
    pet_id INT,
    food_id INT,
    PRIMARY KEY (pet_id, food_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    FOREIGN KEY (food_id) REFERENCES pet_food(food_id)
);

-- Table to store information about suppliers
CREATE TABLE suppliers (
    supplier_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(50),
    phone_number VARCHAR(20),
    email VARCHAR(100),
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

-- Table to associate pets with specific suppliers 
CREATE TABLE pet_supplier_relationship (
    pet_id INT,
    supplier_id INT,
    PRIMARY KEY (pet_id, supplier_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);

-- Table to store information about employees
CREATE TABLE employees (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    position VARCHAR(50),
    hire_date DATE,
    phone_number VARCHAR(20),
    email VARCHAR(100),
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

-- Table to associate employees with specific pets 
CREATE TABLE employee_pet_relationship (
    employee_id INT,
    pet_id INT,
    PRIMARY KEY (employee_id, pet_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id),
    FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
);

-- Insert records into pet_categories
INSERT INTO pet_categories (name) VALUES
('Cats'),
('Dogs'),
('Cows'),
('Buffaloes'),
('Horses'),
('Donkeys'),
('Ducks'),
('Hens'),
('Goats'),
('Sheep'),
('Fishes'),
('Parrots'),
('Turtles'),
('Guinea Pigs'),
('Hamsters'),
('Chickens'),
('Geckos'),
('Rats'),
('Snakes'),
('Rabbits');

-- Insert records into pets with different categories
INSERT INTO pets (name, breed, age, price, category_id, description, image_url)
VALUES
('Bella', 'Labrador Retriever', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Friendly and energetic Labrador Retriever', 'https://example.com/labrador.jpg'),
('Whiskers', 'Persian Cat', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 1, 'Fluffy and calm Persian Cat', 'https://example.com/persiancat.jpg'),
('Max', 'German Shepherd', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Intelligent and loyal German Shepherd', 'https://example.com/germanshepherd.jpg'),
('Simba', 'Sphynx Cat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 1, 'Hairless and affectionate Sphynx Cat', 'https://example.com/sphynxcat.jpg'),
('Luna', 'Golden Retriever', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Playful and affectionate Golden Retriever', 'https://example.com/goldenretriever.jpg'),
('Mochi', 'Siamese Cat', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 1, 'Elegant and vocal Siamese Cat', 'https://example.com/siamesecat.jpg'),
('Charlie', 'Beagle', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Curious and friendly Beagle', 'https://example.com/beagle.jpg'),
('Rocky', 'Doberman Pinscher', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Alert and loyal Doberman Pinscher', 'https://example.com/dobermanpinscher.jpg'),
('Lucy', 'French Bulldog', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Adorable and easygoing French Bulldog', 'https://example.com/frenchbulldog.jpg'),
('Zoe', 'Shetland Sheepdog', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Smart and energetic Shetland Sheepdog', 'https://example.com/sheltie.jpg'),
('Cooper', 'Bulldog', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Determined and gentle Bulldog', 'https://example.com/bulldog.jpg'),
('Lola', 'Pomeranian', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Fluffy and lively Pomeranian', 'https://example.com/pomeranian.jpg'),
('Max', 'Poodle', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Intelligent and hypoallergenic Poodle', 'https://example.com/poodle.jpg'),
('Duke', 'Dalmatian', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Distinctive and friendly Dalmatian', 'https://example.com/dalmatian.jpg'),
('Mia', 'Bernese Mountain Dog', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Gentle and strong Bernese Mountain Dog', 'https://example.com/bernesemountaindog.jpg'),
('Teddy', 'Cavalier King Charles Spaniel', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Gentle and affectionate Cavalier King Charles Spaniel', 'https://example.com/cavalierkingcharles.jpg'),
('Oscar', 'Corgi', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Adorable and outgoing Corgi', 'https://example.com/corgi.jpg'),
('Chloe', 'Great Dane', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Giant and friendly Great Dane', 'https://example.com/greatdane.jpg'),
('Bailey', 'Basset Hound', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Laid-back and charming Basset Hound', 'https://example.com/bassethound.jpg'),
('Oliver', 'Maltese', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Sweet and playful Maltese', 'https://example.com/maltese.jpg'),
('Lily', 'Pug', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Charming and mischievous Pug', 'https://example.com/pug.jpg'),
('Rocky', 'Ragdoll Cat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 1, 'Gentle and laid-back Ragdoll Cat', 'https://example.com/ragdollcat.jpg'),
('Sophie', 'Chihuahua', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Tiny and spirited Chihuahua', 'https://example.com/chihuahua.jpg'),
('Milo', 'Australian Shepherd', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Intelligent and energetic Australian Shepherd', 'https://example.com/australianshepherd.jpg'),
('Ziggy', 'Maine Coon Cat', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 1, 'Fluffy and friendly Maine Coon Cat', 'https://example.com/mainecooncat.jpg'),
('Penny', 'Cocker Spaniel', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 2, 'Gentle and affectionate Cocker Spaniel', 'https://example.com/cockerspaniel.jpg'),
('Daisy', 'Holstein', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 3, 'Gentle and productive Holstein cow', 'https://example.com/holstein.jpg'),
('Thunder', 'Thoroughbred', 4, ROUND(RAND() * (100000 - 100) + 100, 2), 5, 'Elegant and fast Thoroughbred horse', 'https://example.com/thoroughbred.jpg'),
('Nemo', 'Clownfish', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Colorful and playful Clownfish', 'https://example.com/clownfish.jpg'),
('Bessie', 'Jersey', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 3, 'Adorable and milk-producing Jersey cow', 'https://example.com/jersey.jpg'),
('Shadow', 'Arabian', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 5, 'Graceful and intelligent Arabian horse', 'https://example.com/arabian.jpg'),
('Fin', 'Beta Fish', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Colorful and vibrant Beta Fish', 'https://example.com/betafish.jpg'),
('Meadow', 'Simmental', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 3, 'Friendly and robust Simmental cow', 'https://example.com/simmental.jpg'),
('Spirit', 'Quarter Horse', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 5, 'Powerful and versatile Quarter Horse', 'https://example.com/quarterhorse.jpg'),
('Aqua', 'Goldfish', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Classic and graceful Goldfish', 'https://example.com/goldfish.jpg'),
('Rosie', 'Brown Swiss', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 3, 'Docile and high-milk-yielding Brown Swiss cow', 'https://example.com/brownswiss.jpg'),
('Midnight', 'Clydesdale', 4, ROUND(RAND() * (100000 - 100) + 100, 2), 5, 'Strong and majestic Clydesdale horse', 'https://example.com/clydesdale.jpg'),
('Coral', 'Tetra Fish', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Small and colorful Tetra Fish', 'https://example.com/tetrafish.jpg'),
('Buddy', 'Charolais', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 3, 'Hardy and efficient Charolais cow', 'https://example.com/charolais.jpg'),
('Misty', 'Appaloosa', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 5, 'Distinctive and spotted Appaloosa horse', 'https://example.com/appaloosa.jpg'),
('Neptune', 'Guppy', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Colorful and sociable Guppy', 'https://example.com/guppy.jpg'),
('MooMoo', 'Galloway', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 3, 'Adaptable and hardy Galloway cow', 'https://example.com/galloway.jpg'),
('Shadowfax', 'Friesian', 4, ROUND(RAND() * (100000 - 100) + 100, 2), 5, 'Elegant and black-coated Friesian horse', 'https://example.com/friesian.jpg'),
('Bubble', 'Angelfish', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Graceful and delicate Angelfish', 'https://example.com/angelfish.jpg'),
('Bison', 'Bison', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 3, 'Wild and powerful American Bison', 'https://example.com/bison.jpg'),
('Thumper', 'Holland Lop', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 19, 'Adorable and gentle Holland Lop rabbit', 'https://example.com/hollandlop.jpg'),
('Eeyore', 'Miniature Donkey', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 6, 'Calm and small-sized Miniature Donkey', 'https://example.com/miniaturedonkey.jpg'),
('Billy', 'Boer Goat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 9, 'Hardy and productive Boer Goat', 'https://example.com/boergoat.jpg'),
('Snowflake', 'Dorset Sheep', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'White and woolly Dorset Sheep', 'https://example.com/dorsetsheep.jpg'),
('Cotton', 'Jersey Wooly', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 19, 'Fluffy and small Jersey Wooly rabbit', 'https://example.com/jerseywooly.jpg'),
('Burro', 'American Donkey', 4, ROUND(RAND() * (100000 - 100) + 100, 2), 6, 'Sturdy and dependable American Donkey', 'https://example.com/americandonkey.jpg'),
('Cheese', 'Nubian Goat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 9, 'Energetic and long-eared Nubian Goat', 'https://example.com/nubiangoat.jpg'),
('Woolly', 'Suffolk Sheep', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Black-faced and hardy Suffolk Sheep', 'https://example.com/suffolksheep.jpg'),
('Bunny', 'Mini Rex', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 19, 'Soft and velvety Mini Rex rabbit', 'https://example.com/minirex.jpg'),
('Jack', 'Poitou Donkey', 5, ROUND(RAND() * (100000 - 100) + 100, 2), 6, 'Large and shaggy Poitou Donkey', 'https://example.com/poitoudonkey.jpg'),
('Munchkin', 'Pygmy Goat', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 9, 'Tiny and playful Pygmy Goat', 'https://example.com/pygmygoat.jpg'),
('Fleece', 'Merino Sheep', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Fine-wooled and versatile Merino Sheep', 'https://example.com/merinosheep.jpg'),
('Cocoa', 'Rex Rabbit', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 19, 'Velvety and chocolate-colored Rex Rabbit', 'https://example.com/rexrabbit.jpg'),
('Eeyore II', 'American Mammoth Donkey', 6, ROUND(RAND() * (100000 - 100) + 100, 2), 6, 'Tall and gentle American Mammoth Donkey', 'https://example.com/americanmammothdonkey.jpg'),
('Jumper', 'LaMancha Goat', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 9, 'Earless and friendly LaMancha Goat', 'https://example.com/lamanchagoat.jpg'),
('Woolsworth', 'Corriedale Sheep', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Dual-purpose and robust Corriedale Sheep', 'https://example.com/corriedalesheep.jpg'),
('Thistle', 'English Angora', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 19, 'Fluffy and docile English Angora rabbit', 'https://example.com/englishangora.jpg'),
('Gus', 'Mammoth Jackstock Donkey', 7, ROUND(RAND() * (100000 - 100) + 100, 2), 6, 'Large and strong Mammoth Jackstock Donkey', 'https://example.com/mammothjackstockdonkey.jpg'),
('Chewbacca', 'Cashmere Goat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 9, 'Long-haired and charming Cashmere Goat', 'https://example.com/cashmeregoat.jpg'),
('Snowball', 'Shropshire Sheep', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'White-faced and meaty Shropshire Sheep', 'https://example.com/shropshiresheep.jpg'),
('Hopscotch', 'Dwarf Hotot', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 19, 'Small and distinct Dwarf Hotot rabbit', 'https://example.com/dwarphotot.jpg'),
('Charlie', 'Andalusian Donkey', 4, ROUND(RAND() * (100000 - 100) + 100, 2), 6, 'Elegant and sociable Andalusian Donkey', 'https://example.com/andalusiandonkey.jpg'),
('Maple', 'Alpine Goat', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 9, 'Hardy and milk-producing Alpine Goat', 'https://example.com/alpinegoat.jpg'),
('Woolly III', 'Lincoln Sheep', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Large and woolly Lincoln Sheep', 'https://example.com/lincolnsheep.jpg'),
('Fluffernutter', 'Miniature Lop', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 19, 'Laid-back and fluffy Miniature Lop rabbit', 'https://example.com/miniaturelop.jpg'),
('Dobby', 'Poitou Donkey', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 6, 'Shaggy and affectionate Poitou Donkey', 'https://example.com/poitoudonkey2.jpg'),
('Oreo', 'Saanen Goat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 9, 'Distinctive and high-milk-yielding Saanen Goat', 'https://example.com/saanengoat.jpg'),
('Fluffykins', 'Suffolk Sheep', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 10, 'Fluffy and adorable Suffolk Sheep', 'https://example.com/fluffysuffolk.jpg'),
('Rio', 'African Grey Parrot', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 11, 'Intelligent and talkative African Grey Parrot', 'https://example.com/africangreyparrot.jpg'),
('Whiskers', 'Fancy Rat', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 17, 'Curious and social Fancy Rat', 'https://example.com/fancyrat.jpg'),
('Shelly', 'Red-Eared Slider Turtle', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 12, 'Colorful and aquatic Red-Eared Slider Turtle', 'https://example.com/redearedslider.jpg'),
('Bacon', 'Mini Pig', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 18, 'Small and charming Mini Pig', 'https://example.com/minipig.jpg'),
('Kiwi', 'Eclectus Parrot', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 11, 'Vibrant and friendly Eclectus Parrot', 'https://example.com/eclectusparrot.jpg'),
('Squeaky', 'Hooded Rat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 17, 'Adorable and playful Hooded Rat', 'https://example.com/hoodedrat.jpg'),
('Splash', 'Painted Turtle', 4, ROUND(RAND() * (100000 - 100) + 100, 2), 12, 'Colorful and hardy Painted Turtle', 'https://example.com/paintedturtle.jpg'),
('Truffle', 'Teacup Pig', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 18, 'Tiny and delightful Teacup Pig', 'https://example.com/teacuppig.jpg'),
('Mango', 'Sun Conure Parrot', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 11, 'Colorful and energetic Sun Conure Parrot', 'https://example.com/sunconure.jpg'),
('Nibbles', 'Dumbo Rat', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 17, 'Cute and distinctive Dumbo Rat', 'https://example.com/dumborat.jpg'),
('Tortellini', 'Box Turtle', 5, ROUND(RAND() * (100000 - 100) + 100, 2), 12, 'Slow and fascinating Box Turtle', 'https://example.com/boxturtle.jpg'),
('Pebbles', 'Potbelly Pig', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 18, 'Friendly and compact Potbelly Pig', 'https://example.com/potbellypig.jpg'),
('Rio II', 'Macaw Parrot', 3, ROUND(RAND() * (100000 - 100) + 100, 2), 11, 'Colorful and majestic Macaw Parrot', 'https://example.com/macawparrot.jpg'),
('Rizzo', 'Himalayan Rat', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 17, 'Unique and fluffy Himalayan Rat', 'https://example.com/himalayanrat.jpg'),
('Sandy', 'Eastern Box Turtle', 6, ROUND(RAND() * (100000 - 100) + 100, 2), 12, 'Territorial and fascinating Eastern Box Turtle', 'https://example.com/easternboxturtle.jpg'),
('Pippin', 'Juliana Pig', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 18, 'Small and playful Juliana Pig', 'https://example.com/julianapig.jpg'),
('Rainbow', 'Conure Parrot', 2, ROUND(RAND() * (100000 - 100) + 100, 2), 11, 'Colorful and cheerful Conure Parrot', 'https://example.com/conureparrot.jpg'),
('Whiskerino', 'Rex Rat', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 17, 'Curly and friendly Rex Rat', 'https://example.com/rexrat.jpg'),
('Spike', 'Spotted Turtle', 7, ROUND(RAND() * (100000 - 100) + 100, 2), 12, 'Distinctive and patterned Spotted Turtle', 'https://example.com/spottedturtle.jpg'),
('Truffle II', 'Kunekune Pig', 1, ROUND(RAND() * (100000 - 100) + 100, 2), 18, 'Friendly and rotund Kunekune Pig', 'https://example.com/kunekunepig.jpg');

-- Insert records into grooming_services 
INSERT INTO grooming_services (name, description, price, available)
VALUES
('Cat Brushing', 'Gentle brushing for your furry feline friend', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Dog Bathing', 'Refreshing bath for your loyal canine companion', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Cow Grooming', 'Professional grooming for your friendly cow', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Horse Mane Styling', 'Stylish mane grooming for your majestic horse', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Donkey Hoof Trimming', 'Expert trimming for your dependable donkey', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rabbit Fur Trim', 'Delicate fur trimming for your adorable rabbit', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Parrot Feather Styling', 'Colorful feather styling for your talkative parrot', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rat Spa Day', 'Relaxing spa day for your sociable rat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Pig Mud Bath', 'Enjoyable mud bath for your playful pig', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Turtle Shell Polishing', 'Polishing service for your patient turtle', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Goat Horn Grooming', 'Gentle grooming for your horned goat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Sheep Wool Shearing', 'Skillful wool shearing for your fluffy sheep', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Fish Fin Trim', 'Precise fin trimming for your aquatic fish', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Hen Feather Fluffing', 'Fluffing service for your busy hen', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Duck Bill Cleaning', 'Cleaning service for your quacking duck', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Gecko Scale Polishing', 'Gentle scale polishing for your small gecko', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Snake Shedding Assistance', 'Assistance for your snake during shedding', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Guinea Pig Grooming', 'Personalized grooming for your guinea pig', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Hamster Fur Fluffing', 'Fluffing service for your tiny hamster', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Chicken Beak Trimming', 'Careful beak trimming for your clucking chicken', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rat Tail Braiding', 'Creative tail braiding for your fancy rat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Box Turtle Shell Buffing', 'Buffing service for your box turtle shell', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Teacup Pig Mud Spa', 'Mud spa experience for your teacup pig', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Macaw Parrot Feather Coloring', 'Colorful feather coloring for your macaw parrot', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Himalayan Rat Tail Braiding', 'Tail braiding for your stylish himalayan rat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Eastern Box Turtle Pedicure', 'Pedicure service for your box turtle', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Juliana Pig Mud Bath', 'Mud bath for your playful juliana pig', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Sun Conure Parrot Feather Trimming', 'Feather trimming for your sun conure parrot', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Dumbo Rat Ear Cleaning', 'Gentle ear cleaning for your dumbo rat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Painted Turtle Shell Painting', 'Creative shell painting for your painted turtle', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Potbelly Pig Spa Day', 'Relaxing spa day for your potbelly pig', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Siamese Cat Haircut', 'Specialized haircut for your elegant Siamese cat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('German Shepherd Bath', 'Refreshing bath for your loyal German Shepherd', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Holstein Cow Hoof Care', 'Professional hoof care for your Holstein cow', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Arabian Horse Mane Braiding', 'Intricate mane braiding for your Arabian horse', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Miniature Donkey Dental Check', 'Dental check for your adorable miniature donkey', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Flemish Giant Rabbit Grooming', 'Grooming session for your Flemish Giant rabbit', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('African Grey Parrot Feather Styling', 'Feather styling for your intelligent African Grey parrot', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Fancy Rat Spa Retreat', 'Luxurious spa retreat for your fancy rat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Potbelly Pig Mud Facial', 'Mud facial for your charming potbelly pig', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Red-Eared Slider Turtle Shell Polishing', 'Polishing service for your vibrant Red-Eared Slider turtle', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Nubian Goat Horn Trimming', 'Horn trimming for your elegant Nubian goat', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Merino Sheep Wool Styling', 'Styling session for the wool of your Merino sheep', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Betta Fish Fin Enhancement', 'Enhancement of fin beauty for your Betta fish', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Wyandotte Chicken Feather Fluffing', 'Fluffing session for your distinctive Wyandotte chicken', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Indian Runner Duck Beak Polishing', 'Polishing service for your Indian Runner duck', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Leopard Gecko Scale Massage', 'Gentle scale massage for your Leopard Gecko', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Ball Python Shedding Assistance', 'Assistance during shedding for your Ball Python', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Abyssinian Guinea Pig Haircut', 'Haircut session for your Abyssinian Guinea Pig', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Dwarf Hamster Fur Fluffing', 'Fluffing session for your cute Dwarf Hamster', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Silkie Chicken Feather Coloring', 'Coloring session for your silky Silkie chicken', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE);

-- Insert records into pet_grooming_relationship 
INSERT INTO pet_grooming_relationship (pet_id, service_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15),
(16, 16),
(17, 17),
(18, 18),
(19, 19),
(20, 20),
(21, 21),
(22, 22),
(23, 23),
(24, 24),
(25, 25),
(26, 26),
(27, 27),
(28, 28),
(29, 29),
(30, 30),
(31, 31),
(32, 32),
(33, 33),
(34, 34),
(35, 35),
(36, 36),
(37, 37),
(38, 38),
(39, 39),
(40, 40),
(41, 41),
(42, 42),
(43, 43),
(44, 44),
(45, 45),
(46, 46),
(47, 47),
(48, 48),
(49, 49),
(50, 50);

-- Insert records into vaccinations with random prices and availability
INSERT INTO vaccinations (name, description, price, available)
VALUES
('Feline Distemper Vaccine', 'Protects against feline distemper', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Canine Parvovirus Vaccine', 'Prevents canine parvovirus infection', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Bovine Respiratory Syncytial Virus Vaccine', 'Vaccination for bovine respiratory syncytial virus', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Equine Influenza Vaccine', 'Protects horses from equine influenza', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Donkey Tetanus Shot', 'Tetanus protection for donkeys', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rabbit Calicivirus Vaccination', 'Vaccination against rabbit calicivirus', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Parrot Polyomavirus Vaccine', 'Protects parrots from polyomavirus', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rat Infectious Disease Vaccine', 'Vaccine for common infectious diseases in rats', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Swine Flu Vaccine for Pigs', 'Vaccination against swine flu in pigs', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Turtle Shell Rot Vaccine', 'Protects turtles from shell rot disease', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Goat Clostridium Perfringens Vaccine', 'Vaccination for Clostridium perfringens in goats', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Sheep Foot Rot Vaccine', 'Vaccine to prevent foot rot in sheep', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Beta Fish Fin Rot Vaccine', 'Protects Betta fish from fin rot disease', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Chicken Newcastle Disease Vaccine', 'Prevents Newcastle disease in chickens', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Duck Avian Influenza Vaccination', 'Vaccination for avian influenza in ducks', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Gecko Metabolic Bone Disease Vaccine', 'Vaccine to prevent metabolic bone disease in geckos', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Snake Respiratory Infection Vaccine', 'Vaccination against respiratory infections in snakes', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Guinea Pig Bordetella Vaccine', 'Vaccine for Bordetella in guinea pigs', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Hamster Tyzzer\'s Disease Vaccine', 'Vaccination for Tyzzer\'s disease in hamsters', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Silkie Chicken Marek\'s Disease Vaccine', 'Prevents Marek\'s disease in Silkie chickens', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Feline Leukemia Vaccine', 'Protects against feline leukemia', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Canine Coronavirus Vaccine', 'Prevents canine coronavirus infection', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Bovine Brucellosis Vaccine', 'Vaccine for bovine brucellosis', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Equine West Nile Virus Vaccine', 'Protects horses from West Nile virus', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Donkey Strangles Vaccination', 'Vaccination against strangles in donkeys', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rabbit Myxomatosis Shot', 'Vaccination for rabbit myxomatosis', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Parrot Psittacosis Vaccine', 'Protects parrots from psittacosis', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rat Leptospirosis Vaccination', 'Vaccine for leptospirosis in rats', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Swine Erysipelas Vaccine', 'Vaccination against erysipelas in pigs', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Turtle Salmonella Shot', 'Protects turtles from salmonella infection', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Goat Caprine Arthritis Encephalitis Vaccine', 'Vaccination for caprine arthritis encephalitis in goats', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Sheep Internal Parasite Vaccine', 'Vaccine to prevent internal parasites in sheep', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Beta Fish Velvet Disease Vaccine', 'Protects Betta fish from velvet disease', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Chicken Fowl Pox Vaccine', 'Prevents fowl pox in chickens', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Duck Botulism Vaccination', 'Vaccination for botulism in ducks', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Gecko Parasitic Infection Vaccine', 'Vaccine against parasitic infections in geckos', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Snake Scale Rot Vaccine', 'Vaccination for scale rot disease in snakes', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Guinea Pig Pneumonia Vaccine', 'Vaccine for pneumonia in guinea pigs', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Hamster Wet Tail Shot', 'Vaccination against wet tail disease in hamsters', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Silkie Chicken Coccidiosis Vaccine', 'Prevents coccidiosis in Silkie chickens', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Feline Calicivirus Vaccine', 'Vaccine for feline calicivirus', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Canine Bordetella Vaccine', 'Vaccination for canine bordetella', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Bovine Clostridium Perfringens Vaccine', 'Vaccine for Clostridium perfringens in cattle', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Equine Tetanus Shot', 'Tetanus protection for horses', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Donkey Equine Herpesvirus Vaccine', 'Vaccination for equine herpesvirus in donkeys', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rabbit Viral Hemorrhagic Disease Vaccine', 'Vaccine against viral hemorrhagic disease in rabbits', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Parrot Polyomavirus Booster Shot', 'Additional protection against polyomavirus in parrots', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Rat Rat Bite Fever Vaccine', 'Vaccination for rat bite fever in rats', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Swine PRRS Vaccine', 'Vaccine for porcine reproductive and respiratory syndrome in pigs', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE),
('Turtle Shell Abscess Vaccine', 'Vaccine to prevent shell abscess in turtles', ROUND(RAND() * (9500 - 500) + 500, 2), TRUE);

-- Insert records into pet_vaccination_relationship with consecutive pet_id and vaccination_id 
INSERT INTO pet_vaccination_relationship (pet_id, vaccination_id)
VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5),
(6, 6), (7, 7), (8, 8), (9, 9), (10, 10),
(11, 11), (12, 12), (13, 13), (14, 14), (15, 15),
(16, 16), (17, 17), (18, 18), (19, 19), (20, 20),
(21, 21), (22, 22), (23, 23), (24, 24), (25, 25),
(26, 26), (27, 27), (28, 28), (29, 29), (30, 30),
(31, 31), (32, 32), (33, 33), (34, 34), (35, 35),
(36, 36), (37, 37), (38, 38), (39, 39), (40, 40),
(41, 41), (42, 42), (43, 43), (44, 44), (45, 45),
(46, 46), (47, 47), (48, 48), (49, 49), (50, 50);

-- Insert records into the addresses table 
INSERT INTO addresses (street, city, state, zip_code) VALUES
('123 Oak Street', 'Springfield', 'Illinois', '62701'),
('456 Pine Avenue', 'Maplewood', 'New Jersey', '07040'),
('789 Elm Lane', 'Lexington', 'Kentucky', '40502'),
('321 Maple Street', 'Boulder', 'Colorado', '80302'),
('654 Cedar Avenue', 'Portland', 'Oregon', '97201'),
('987 Birch Lane', 'Austin', 'Texas', '78701'),
('147 Redwood Street', 'Raleigh', 'North Carolina', '27601'),
('258 Walnut Avenue', 'Madison', 'Wisconsin', '53703'),
('369 Oak Lane', 'Bozeman', 'Montana', '59715'),
('951 Pine Street', 'Santa Fe', 'New Mexico', '87501'),
('753 Elm Avenue', 'Asheville', 'North Carolina', '28801'),
('864 Birch Lane', 'Burlington', 'Vermont', '05401'),
('246 Cedar Street', 'Savannah', 'Georgia', '31401'),
('135 Oak Avenue', 'Ann Arbor', 'Michigan', '48104'),
('579 Pine Lane', 'Bellingham', 'Washington', '98225'),
('802 Maple Street', 'Ithaca', 'New York', '14850'),
('714 Cedar Avenue', 'San Luis Obispo', 'California', '93401'),
('625 Oak Lane', 'Providence', 'Rhode Island', '02903'),
('937 Birch Street', 'Charleston', 'South Carolina', '29401'),
('648 Maple Avenue', 'Athens', 'Georgia', '30601'),
('360 Elm Lane', 'Eugene', 'Oregon', '97401'),
('720 Pine Street', 'Knoxville', 'Tennessee', '37902'),
('489 Cedar Avenue', 'Fayetteville', 'Arkansas', '72701'),
('271 Oak Lane', 'Flagstaff', 'Arizona', '86001'),
('146 Maple Street', 'Missoula', 'Montana', '59801'),
('582 Birch Avenue', 'Lawrence', 'Kansas', '66044'),
('793 Pine Lane', 'Sarasota', 'Florida', '34236'),
('902 Oak Street', 'Birmingham', 'Alabama', '35203'),
('115 Elm Avenue', 'Spokane', 'Washington', '99201'),
('223 Pine Lane', 'Tallahassee', 'Florida', '32301'),
('334 Cedar Street', 'Boise', 'Idaho', '83702'),
('445 Elm Avenue', 'Wichita', 'Kansas', '67202'),
('556 Birch Lane', 'Buffalo', 'New York', '14202'),
('667 Oak Street', 'Omaha', 'Nebraska', '68102'),
('778 Maple Avenue', 'Des Moines', 'Iowa', '50309'),
('889 Cedar Lane', 'Rochester', 'Minnesota', '55901'),
('990 Elm Street', 'Mobile', 'Alabama', '36602'),
('101 Pine Avenue', 'Harrisburg', 'Pennsylvania', '17102'),
('212 Oak Lane', 'Montgomery', 'Alabama', '36104'),
('323 Birch Street', 'Cheyenne', 'Wyoming', '82001'),
('434 Maple Avenue', 'Juneau', 'Alaska', '99801'),
('545 Cedar Lane', 'Concord', 'New Hampshire', '03301'),
('656 Elm Street', 'Lincoln', 'Nebraska', '68502'),
('767 Pine Avenue', 'Pierre', 'South Dakota', '57101'),
('878 Oak Lane', 'Bismarck', 'North Dakota', '58501'),
('989 Maple Street', 'Montpelier', 'Vermont', '05602'),
('1000 Birch Avenue', 'Helena', 'Montana', '59601'),
('111 Pine Street', 'Carson City', 'Nevada', '89701'),
('122 Cedar Lane', 'Dover', 'Delaware', '19901'),
('233 Spruce Avenue', 'Madison', 'Wisconsin', '53703'),
('344 Birch Street', 'Olympia', 'Washington', '98501'),
('455 Cedar Lane', 'Austin', 'Texas', '78701'),
('566 Oak Avenue', 'Little Rock', 'Arkansas', '72201'),
('677 Pine Street', 'Jackson', 'Mississippi', '39201'),
('788 Maple Lane', 'Annapolis', 'Maryland', '21401'),
('899 Elm Street', 'Columbia', 'South Carolina', '29201'),
('910 Cedar Avenue', 'Frankfort', 'Kentucky', '40601'),
('121 Pine Lane', 'Honolulu', 'Hawaii', '96813'),
('232 Birch Street', 'Phoenix', 'Arizona', '85001'),
('343 Oak Avenue', 'Denver', 'Colorado', '80202'),
('454 Maple Lane', 'Sacramento', 'California', '95814'),
('565 Cedar Street', 'Hartford', 'Connecticut', '06103'),
('676 Elm Lane', 'Montgomery', 'Alabama', '36104'),
('787 Pine Street', 'Salem', 'Oregon', '97301'),
('898 Birch Lane', 'Topeka', 'Kansas', '66603'),
('909 Oak Street', 'Providence', 'Rhode Island', '02903'),
('120 Maple Avenue', 'Columbus', 'Ohio', '43215'),
('231 Cedar Street', 'Richmond', 'Virginia', '23219'),
('342 Elm Lane', 'Jefferson City', 'Missouri', '65101');

-- Insert records into customers
INSERT INTO customers (first_name, last_name, email, phone_number, address_id ) VALUES
('Alice', 'Johnson', 'alice.johnson@example.com', '5551234000', FLOOR(RAND() * 50) + 1),
('Bob', 'Smith', 'bob.smith@example.com', '5555678001', FLOOR(RAND() * 50) + 1),
('Charlie', 'Williams', 'charlie.williams@example.com', '5559012002', FLOOR(RAND() * 50) + 1),
('David', 'Davis', 'david.davis@example.com', '5553456003', FLOOR(RAND() * 50) + 1),
('Emma', 'Brown', 'emma.brown@example.com', '5557890004', FLOOR(RAND() * 50) + 1),
('Frank', 'Taylor', 'frank.taylor@example.com', '5552345005', FLOOR(RAND() * 50) + 1),
('Grace', 'Miller', 'grace.miller@example.com', '5556789006', FLOOR(RAND() * 50) + 1),
('Henry', 'Wilson', 'henry.wilson@example.com', '5550123007', FLOOR(RAND() * 50) + 1),
('Ivy', 'Moore', 'ivy.moore@example.com', '5554567008', FLOOR(RAND() * 50) + 1),
('Jack', 'Anderson', 'jack.anderson@example.com', '5558901009', FLOOR(RAND() * 50) + 1),
('Katie', 'Thomas', 'katie.thomas@example.com', '5551234010', FLOOR(RAND() * 50) + 1),
('Leo', 'Martinez', 'leo.martinez@example.com', '5555678011', FLOOR(RAND() * 50) + 1),
('Mia', 'Jones', 'mia.jones@example.com', '5559012012', FLOOR(RAND() * 50) + 1),
('Noah', 'Hernandez', 'noah.hernandez@example.com', '5553456013', FLOOR(RAND() * 50) + 1),
('Olivia', 'Young', 'olivia.young@example.com', '5557890014', FLOOR(RAND() * 50) + 1),
('Peter', 'Scott', 'peter.scott@example.com', '5552345015', FLOOR(RAND() * 50) + 1),
('Quinn', 'King', 'quinn.king@example.com', '5556789016', FLOOR(RAND() * 50) + 1),
('Rachel', 'Baker', 'rachel.baker@example.com', '5550123017', FLOOR(RAND() * 50) + 1),
('Sam', 'Evans', 'sam.evans@example.com', '5554567018', FLOOR(RAND() * 50) + 1),
('Tina', 'Garcia', 'tina.garcia@example.com', '5558901019', FLOOR(RAND() * 50) + 1),
('Ulysses', 'White', 'ulysses.white@example.com', '5551234020', FLOOR(RAND() * 50) + 1),
('Victoria', 'Lopez', 'victoria.lopez@example.com', '5555678021', FLOOR(RAND() * 50) + 1),
('Walter', 'Perez', 'walter.perez@example.com', '5559012022', FLOOR(RAND() * 50) + 1),
('Xena', 'Gomez', 'xena.gomez@example.com', '5553456023', FLOOR(RAND() * 50) + 1),
('Yasmine', 'Rivera', 'yasmine.rivera@example.com', '5557890024', FLOOR(RAND() * 50) + 1),
('Zane', 'Reyes', 'zane.reyes@example.com', '5552345025', FLOOR(RAND() * 50) + 1),
('Ava', 'Turner', 'ava.turner@example.com', '5556789026', FLOOR(RAND() * 50) + 1),
('Ben', 'Collins', 'ben.collins@example.com', '5550123027', FLOOR(RAND() * 50) + 1),
('Chloe', 'Hill', 'chloe.hill@example.com', '5554567028', FLOOR(RAND() * 50) + 1),
('Daniel', 'Russell', 'daniel.russell@example.com', '5558901029', FLOOR(RAND() * 50) + 1),
('Emily', 'Carter', 'emily.carter@example.com', '5551234030', FLOOR(RAND() * 50) + 1),
('Finn', 'Diaz', 'finn.diaz@example.com', '5555678031', FLOOR(RAND() * 50) + 1),
('Gina', 'Morgan', 'gina.morgan@example.com', '5559012032', FLOOR(RAND() * 50) + 1),
('Hank', 'Reed', 'hank.reed@example.com', '5553456033', FLOOR(RAND() * 50) + 1),
('Isla', 'Ward', 'isla.ward@example.com', '5557890034', FLOOR(RAND() * 50) + 1),
('Jake', 'Fisher', 'jake.fisher@example.com', '5552345035', FLOOR(RAND() * 50) + 1),
('Kara', 'Bailey', 'kara.bailey@example.com', '5556789036', FLOOR(RAND() * 50) + 1),
('Liam', 'Owens', 'liam.owens@example.com', '5550123037', FLOOR(RAND() * 50) + 1),
('Mara', 'Nelson', 'mara.nelson@example.com', '5554567038', FLOOR(RAND() * 50) + 1),
('Nathan', 'Hayes', 'nathan.hayes@example.com', '5558901039', FLOOR(RAND() * 50) + 1),
('Oliver', 'Vargas', 'oliver.vargas@example.com', '5551234040', FLOOR(RAND() * 50) + 1),
('Paige', 'Gardner', 'paige.gardner@example.com', '5555678041', FLOOR(RAND() * 50) + 1),
('Quincy', 'Perkins', 'quincy.perkins@example.com', '5559012042', FLOOR(RAND() * 50) + 1),
('Ruby', 'Isaacs', 'ruby.isaacs@example.com', '5553456043', FLOOR(RAND() * 50) + 1),
('Samuel', 'Gibson', 'samuel.gibson@example.com', '5557890044', FLOOR(RAND() * 50) + 1),
('Tessa', 'Harrison', 'tessa.harrison@example.com', '5552345045', FLOOR(RAND() * 50) + 1),
('Uma', 'Lynch', 'uma.lynch@example.com', '5556789046', FLOOR(RAND() * 50) + 1),
('Victor', 'Sharp', 'victor.sharp@example.com', '5550123047', FLOOR(RAND() * 50) + 1),
('Wendy', 'McCarthy', 'wendy.mccarthy@example.com', '5554567048', FLOOR(RAND() * 50) + 1),
('Xander', 'Freeman', 'xander.freeman@example.com', '5558901049', FLOOR(RAND() * 50) + 1),
('Yara', 'Vaughn', 'yara.vaughn@example.com', '5551234050', FLOOR(RAND() * 50) + 1),
('Zach', 'Ingram', 'zach.ingram@example.com', '5555678051', FLOOR(RAND() * 50) + 1),
('Alex', 'Cooper', 'alex.cooper@example.com', '5551234061', FLOOR(RAND() * 50) + 1),
('Bella', 'Gordon', 'bella.gordon@example.com', '5555678072', FLOOR(RAND() * 50) + 1),
('Cameron', 'Holt', 'cameron.holt@example.com', '5559012083', FLOOR(RAND() * 50) + 1),
('Dylan', 'Barnes', 'dylan.barnes@example.com', '5553456094', FLOOR(RAND() * 50) + 1),
('Ella', 'Fleming', 'ella.fleming@example.com', '5557890105', FLOOR(RAND() * 50) + 1),
('Felix', 'Owens', 'felix.owens@example.com', '5552345106', FLOOR(RAND() * 50) + 1),
('Gabriella', 'Lopez', 'gabriella.lopez@example.com', '5556789107', FLOOR(RAND() * 50) + 1),
('Hudson', 'Nelson', 'hudson.nelson@example.com', '5550123108', FLOOR(RAND() * 50) + 1),
('Isaac', 'Fisher', 'isaac.fisher@example.com', '5554567109', FLOOR(RAND() * 50) + 1),
('Jade', 'Vargas', 'jade.vargas@example.com', '5558901110', FLOOR(RAND() * 50) + 1),
('Kai', 'Turner', 'kai.turner@example.com', '5551234111', FLOOR(RAND() * 50) + 1),
('Lily', 'Collins', 'lily.collins@example.com', '5555678112', FLOOR(RAND() * 50) + 1),
('Mason', 'Hayes', 'mason.hayes@example.com', '5559012113', FLOOR(RAND() * 50) + 1),
('Natalie', 'Russell', 'natalie.russell@example.com', '5553456124', FLOOR(RAND() * 50) + 1),
('Owen', 'Carter', 'owen.carter@example.com', '5557890125', FLOOR(RAND() * 50) + 1),
('Penelope', 'Perkins', 'penelope.perkins@example.com', '5552345126', FLOOR(RAND() * 50) + 1),
('Quinn', 'Isaacs', 'quinn.isaacs@example.com', '5556789127', FLOOR(RAND() * 50) + 1),
('Riley', 'Gibson', 'riley.gibson@example.com', '5550123128', FLOOR(RAND() * 50) + 1),
('Sawyer', 'Harrison', 'sawyer.harrison@example.com', '5554567129', FLOOR(RAND() * 50) + 1),
('Taylor', 'Lynch', 'taylor.lynch@example.com', '5558901130', FLOOR(RAND() * 50) + 1),
('Uriah', 'Sharp', 'uriah.sharp@example.com', '5551234131', FLOOR(RAND() * 50) + 1),
('Vivian', 'McCarthy', 'vivian.mccarthy@example.com', '5555678132', FLOOR(RAND() * 50) + 1),
('Wyatt', 'Freeman', 'wyatt.freeman@example.com', '5559012133', FLOOR(RAND() * 50) + 1),
('Xena', 'Vaughn', 'xena.vaughn@example.com', '5553456144', FLOOR(RAND() * 50) + 1),
('Yasmine', 'Ingram', 'yasmine.ingram@example.com', '5557890145', FLOOR(RAND() * 50) + 1),
('Zane', 'Rogers', 'zane.rogers@example.com', '5552345146', FLOOR(RAND() * 50) + 1),
('Amara', 'Hansen', 'amara.hansen@example.com', '5556789147', FLOOR(RAND() * 50) + 1),
('Bryce', 'Woods', 'bryce.woods@example.com', '5550123148', FLOOR(RAND() * 50) + 1),
('Cora', 'Benson', 'cora.benson@example.com', '5554567149', FLOOR(RAND() * 50) + 1),
('Dante', 'Hodge', 'dante.hodge@example.com', '5558901150', FLOOR(RAND() * 50) + 1),
('Eliza', 'Wade', 'eliza.wade@example.com', '5551234151', FLOOR(RAND() * 50) + 1),
('Finn', 'Mccarthy', 'finn.mccarthy@example.com', '5555678152', FLOOR(RAND() * 50) + 1),
('Giselle', 'Chan', 'giselle.chan@example.com', '5559012153', FLOOR(RAND() * 50) + 1),
('Hugo', 'Pugh', 'hugo.pugh@example.com', '5553456164', FLOOR(RAND() * 50) + 1),
('Iris', 'Brock', 'iris.brock@example.com', '5557890165', FLOOR(RAND() * 50) + 1),
('Jax', 'Tate', 'jax.tate@example.com', '5552345166', FLOOR(RAND() * 50) + 1),
('Kara', 'Diaz', 'kara.diaz@example.com', '5556789167', FLOOR(RAND() * 50) + 1),
('Landon', 'Wolfe', 'landon.wolfe@example.com', '5550123168', FLOOR(RAND() * 50) + 1),
('Mila', 'Pierce', 'mila.pierce@example.com', '5554567169', FLOOR(RAND() * 50) + 1),
('Nash', 'Booth', 'nash.booth@example.com', '5558901170', FLOOR(RAND() * 50) + 1),
('Olive', 'Davenport', 'olive.davenport@example.com', '5551234171', FLOOR(RAND() * 50) + 1),
('Paxton', 'Sawyer', 'paxton.sawyer@example.com', '5555678172', FLOOR(RAND() * 50) + 1),
('Quincy', 'Vaughan', 'quincy.vaughan@example.com', '5559012173', FLOOR(RAND() * 50) + 1),
('Reese', 'Hartley', 'reese.hartley@example.com', '5553456184', FLOOR(RAND() * 50) + 1),
('Sasha', 'Brock', 'sasha.brock@example.com', '5557890185', FLOOR(RAND() * 50) + 1),
('Tyson', 'Mercer', 'tyson.mercer@example.com', '5552345186', FLOOR(RAND() * 50) + 1),
('Ursula', 'Langley', 'ursula.langley@example.com', '5556789187', FLOOR(RAND() * 50) + 1),
('Violet', 'Crawford', 'violet.crawford@example.com', '5550123188', FLOOR(RAND() * 50) + 1),
('Wesley', 'Werner', 'wesley.werner@example.com', '5554567189', FLOOR(RAND() * 50) + 1),
('Xander', 'Knox', 'xander.knox@example.com', '5558901190', FLOOR(RAND() * 50) + 1),
('Yara', 'Walter', 'yara.walter@example.com', '5551234191', FLOOR(RAND() * 50) + 1),
('Zion', 'Mayer', 'zion.mayer@example.com', '5555678192', FLOOR(RAND() * 50) + 1);

-- Insert records into transactions
INSERT INTO transactions (customer_id, pet_id, transaction_date, amount, transaction_status) VALUES
-- Successful Transactions
(1, 1, '2022-01-15', RAND() * (100000 - 500) + 500, 'Success'),
(2, 2, '2022-02-20', RAND() * (100000 - 500) + 500, 'Success'),
(3, 3, '2022-03-25', RAND() * (100000 - 500) + 500, 'Success'),
(4, 4, '2022-04-30', RAND() * (100000 - 500) + 500, 'Success'),
(5, 5, '2022-05-05', RAND() * (100000 - 500) + 500, 'Success'),
(6, 6, '2022-06-10', RAND() * (100000 - 500) + 500, 'Success'),
(7, 7, '2022-07-15', RAND() * (100000 - 500) + 500, 'Success'),
(8, 8, '2022-08-20', RAND() * (100000 - 500) + 500, 'Success'),
(9, 9, '2022-09-25', RAND() * (100000 - 500) + 500, 'Success'),
(10, 10, '2022-10-30', RAND() * (100000 - 500) + 500, 'Success'),
(11, 11, '2022-11-05', RAND() * (100000 - 500) + 500, 'Success'),
(12, 12, '2022-12-10', RAND() * (100000 - 500) + 500, 'Success'),
(13, 13, '2023-01-15', RAND() * (100000 - 500) + 500, 'Success'),
(14, 14, '2023-02-20', RAND() * (100000 - 500) + 500, 'Success'),
(15, 15, '2023-03-25', RAND() * (100000 - 500) + 500, 'Success'),
(16, 16, '2023-04-30', RAND() * (100000 - 500) + 500, 'Success'),
(17, 17, '2023-05-05', RAND() * (100000 - 500) + 500, 'Success'),
(18, 18, '2023-06-10', RAND() * (100000 - 500) + 500, 'Success'),
(19, 19, '2023-07-15', RAND() * (100000 - 500) + 500, 'Success'),
(20, 20, '2023-08-20', RAND() * (100000 - 500) + 500, 'Success'),
(21, 21, '2023-09-25', RAND() * (100000 - 500) + 500, 'Success'),
(22, 22, '2023-10-30', RAND() * (100000 - 500) + 500, 'Success'),
(23, 23, '2023-11-05', RAND() * (100000 - 500) + 500, 'Success'),
(24, 24, '2023-12-10', RAND() * (100000 - 500) + 500, 'Success'),
(25, 25, '2024-01-15', RAND() * (100000 - 500) + 500, 'Success'),
(26, 26, '2024-02-20', RAND() * (100000 - 500) + 500, 'Success'),
(27, 27, '2024-03-25', RAND() * (100000 - 500) + 500, 'Success'),
(28, 28, '2024-04-30', RAND() * (100000 - 500) + 500, 'Success'),
(29, 29, '2024-05-05', RAND() * (100000 - 500) + 500, 'Success'),
(30, 30, '2024-06-10', RAND() * (100000 - 500) + 500, 'Success'),
-- Failed Transactions
(31, 31, '2024-07-15', RAND() * (100000 - 500) + 500, 'Failed'),
(32, 32, '2024-08-20', RAND() * (100000 - 500) + 500, 'Failed'),
(33, 33, '2024-09-25', RAND() * (100000 - 500) + 500, 'Failed'),
(34, 34, '2024-10-30', RAND() * (100000 - 500) + 500, 'Failed'),
(35, 35, '2024-11-05', RAND() * (100000 - 500) + 500, 'Failed'),
(36, 36, '2024-12-10', RAND() * (100000 - 500) + 500, 'Failed'),
(37, 37, '2025-01-15', RAND() * (100000 - 500) + 500, 'Failed'),
(38, 38, '2025-02-20', RAND() * (100000 - 500) + 500, 'Failed'),
(39, 39, '2025-03-25', RAND() * (100000 - 500) + 500, 'Failed'),
(40, 40, '2025-04-30', RAND() * (100000 - 500) + 500, 'Failed'),
(41, 41, '2025-05-05', RAND() * (100000 - 500) + 500, 'Failed'),
(42, 42, '2025-06-10', RAND() * (100000 - 500) + 500, 'Failed'),
(43, 43, '2025-07-15', RAND() * (100000 - 500) + 500, 'Failed'),
(44, 44, '2025-08-20', RAND() * (100000 - 500) + 500, 'Failed'),
(45, 45, '2025-09-25', RAND() * (100000 - 500) + 500, 'Failed'),
(46, 46, '2025-10-30', RAND() * (100000 - 500) + 500, 'Failed'),
(47, 47, '2025-11-05', RAND() * (100000 - 500) + 500, 'Failed'),
(48, 48, '2025-12-10', RAND() * (100000 - 500) + 500, 'Failed'),
(49, 49, '2026-01-15', RAND() * (100000 - 500) + 500, 'Failed'),
(50, 50, '2026-02-20', RAND() * (100000 - 500) + 500, 'Failed');


-- Insert records into pet_food 
INSERT INTO pet_food (name, brand, type, quantity, price)
VALUES
('Premium Cat Food', 'Whiskers', 'Dry', 20, 1200.50),
('Healthy Dog Bites', 'Pawsome', 'Wet', 15, 850.75),
('Organic Rabbit Pellets', 'Hop Nibble', 'Dry', 25, 600.25),
('Natural Fish Treats', 'Finest Catch', 'Wet', 18, 950.00),
('Grain-Free Puppy Chow', 'Tail Wag', 'Dry', 22, 1100.25),
('Fresh Chicken Delight', 'Feather Feast', 'Wet', 12, 750.50),
('Nutritious Turtle Sticks', 'Shell Care', 'Dry', 30, 800.75),
('Feathered Friends Mix', 'Tweet Treats', 'Wet', 14, 600.00),
('Goat Milk Blend', 'Hoof & Horn', 'Dry', 28, 900.25),
('Soft Lamb Bites', 'Baa Treats', 'Wet', 16, 700.75),
('Ocean Delight Fish Food', 'Seaside Scales', 'Dry', 24, 1300.50),
('Fresh Veggie Crunch', 'Garden Goodies', 'Wet', 20, 1000.75),
('Crunchy Parrot Pellets', 'Colorful Chirp', 'Dry', 26, 850.25),
('Gourmet Hamster Feast', 'Tiny Treats', 'Wet', 10, 550.00),
('Country Chicken Morsels', 'Farm Fresh', 'Dry', 18, 1200.25),
('Super Seeds Blend', 'Nutty Nibbles', 'Wet', 25, 950.50),
('Snappy Snail Treats', 'Slow Munch', 'Dry', 15, 700.25),
('Wholesome Rat Mix', 'Rodent Riches', 'Wet', 23, 600.75),
('Slimy Snake Supper', 'Slither Snacks', 'Dry', 12, 800.50),
('Leafy Greens Munch', 'Turtle Tidbits', 'Wet', 30, 1100.00),
('Guinea Pig Granola', 'Piggie Paradise', 'Dry', 20, 900.75),
('Sweet Carrot Drops', 'Hop Along', 'Wet', 14, 750.25),
('Hearty Horse Hay', 'Stable Supreme', 'Dry', 26, 1300.25),
('Duck Delight Pellets', 'Quacker Quisine', 'Wet', 18, 1000.50),
('Fruity Ferret Treats', 'Ferret Feast', 'Dry', 22, 850.75),
('Piggy Pellets', 'Snout Snacks', 'Wet', 16, 600.00),
('Moo Moo Cow Chow', 'Pasture Perfect', 'Dry', 30, 1100.25),
('Chick-N-Cheese Medley', 'Cluck Cluck', 'Wet', 10, 700.50),
('Sardine Surprise', 'Sea Symphony', 'Dry', 28, 950.25),
('Healthy Hedgehog Blend', 'Spiky Snacks', 'Wet', 24, 800.75),
('Kitty Crunch Delight', 'Meow Munchies', 'Dry', 18, 850.25),
('Beefy Bites for Dogs', 'Woof N Chew', 'Wet', 14, 700.50),
('Herbivore Bunny Pellets', 'Green Graze', 'Dry', 22, 950.75),
('Salmon Sensation Treats', 'Finned Feast', 'Wet', 20, 1100.25),
('Puppy Power Kibble', 'Tiny Tails', 'Dry', 25, 1200.50),
('Chicken Pate for Pups', 'Pawlicking Good', 'Wet', 16, 900.75),
('Algae-Infused Turtle Bites', 'Shell Savvy', 'Dry', 30, 1000.00),
('Melody of Tweets Mix', 'Singing Serenade', 'Wet', 12, 800.50),
('Goat Cheese Delight', 'Hoof & Whisker', 'Dry', 28, 750.25),
('Soft Lamb Stew', 'Baa Bonanza', 'Wet', 15, 600.75),
('Tropical Fish Delicacy', 'Aqua Appetite', 'Dry', 24, 1300.50),
('Garden Greens Combo', 'Leafy Luxuries', 'Wet', 18, 1000.75),
('Parrot Paradise Pellets', 'Vibrant Vibes', 'Dry', 26, 950.25),
('Hamster Heaven Feast', 'Pocket Paradise', 'Wet', 10, 550.00),
('Country Chicken Chunks', 'Farm Fresh Fare', 'Dry', 20, 1200.25),
('Nutty Birdie Mix', 'Feathered Fusion', 'Wet', 25, 850.50),
('Crunchy Snail Treats', 'Spiral Snacks', 'Dry', 15, 700.25),
('Rich Rat Regimen', 'Rodent Royalty', 'Wet', 23, 600.75),
('Venomous Viper Vittles', 'Slithery Suppers', 'Dry', 12, 800.50),
('Leafy Turtle Treats', 'Shell Smorgasbord', 'Wet', 30, 1100.00);

-- Insert records into pet_food_relationship with specific associations
INSERT INTO pet_food_relationship (pet_id, food_id) VALUES
(1, 5),
(2, 15),
(3, 20),
(4, 10),
(5, 25),
(6, 35),
(7, 45),
(8, 50),
(9, 30),
(10, 40),
(11, 3),
(12, 18),
(13, 8),
(14, 28),
(15, 48),
(16, 38),
(17, 13),
(18, 23),
(19, 43),
(20, 33),
(21, 1),
(22, 11),
(23, 16),
(24, 6),
(25, 21),
(26, 31),
(27, 41),
(28, 46),
(29, 26),
(30, 36),
(31, 2),
(32, 12),
(33, 17),
(34, 7),
(35, 22),
(36, 32),
(37, 42),
(38, 47),
(39, 27),
(40, 37),
(41, 4),
(42, 14),
(43, 9),
(44, 29),
(45, 49),
(46, 39),
(47, 24),
(48, 34),
(49, 44),
(50, 19);

-- Insert records into suppliers for pet varieties
INSERT INTO suppliers (name, contact_person, phone_number, email, address_id) VALUES
('Pet Supply Co', 'John Smith', '123-456-7890', 'john@example.com', FLOOR(RAND() * 50) + 1),
('Animal Haven Suppliers', 'Jane Doe', '987-654-3210', 'jane@example.com', FLOOR(RAND() * 50) + 1),
('Farmers Pet Emporium', 'Bob Johnson', '555-123-4567', 'bob@example.com', FLOOR(RAND() * 50) + 1),
('Feathered Friends Distributors', 'Alice Wilson', '111-222-3333', 'alice@example.com', FLOOR(RAND() * 50) + 1),
('Furry Companions Supply', 'Charlie Brown', '888-777-6666', 'charlie@example.com', FLOOR(RAND() * 50) + 1),
('Reptile Realm Suppliers', 'Eva Garcia', '444-555-6666', 'eva@example.com', FLOOR(RAND() * 50) + 1),
('Paws and Claws Wholesale', 'David Anderson', '777-888-9999', 'david@example.com', FLOOR(RAND() * 50) + 1),
('Fluffy Friends Importers', 'Samantha Turner', '666-555-4444', 'samantha@example.com', FLOOR(RAND() * 50) + 1),
('Hoof and Horn Livestock Suppliers', 'Mark Davis', '222-333-4444', 'mark@example.com', FLOOR(RAND() * 50) + 1),
('Aquatic Wonders Distributors', 'Karen White', '999-888-7777', 'karen@example.com', FLOOR(RAND() * 50) + 1),
('Avian Adventures Suppliers', 'Kevin Thomas', '444-333-2222', 'kevin@example.com', FLOOR(RAND() * 50) + 1),
('Small Critters Supply Co', 'Laura Miller', '333-444-5555', 'laura@example.com', FLOOR(RAND() * 50) + 1),
('Rodent Retailers', 'Mike Harris', '777-666-5555', 'mike@example.com', FLOOR(RAND() * 50) + 1),
('Hoppy Homes Distributors', 'Olivia Brown', '555-666-7777', 'olivia@example.com', FLOOR(RAND() * 50) + 1),
('Shell Shack Importers', 'Henry Taylor', '111-222-3333', 'henry@example.com', FLOOR(RAND() * 50) + 1),
('Swim Fin Suppliers', 'Grace Robinson', '888-777-6666', 'grace@example.com', FLOOR(RAND() * 50) + 1),
('Feathered Friends Wholesale', 'Tom Parker', '666-555-4444', 'tom@example.com', FLOOR(RAND() * 50) + 1),
('Furry Friends Distributors', 'Mia Wilson', '555-666-7777', 'mia@example.com', FLOOR(RAND() * 50) + 1),
('Squeaky Toy Importers', 'Chris Adams', '333-444-5555', 'chris@example.com', FLOOR(RAND() * 50) + 1),
('Pet Paradise Supply Co', 'Sophie Turner', '222-333-4444', 'sophie@example.com', FLOOR(RAND() * 50) + 1),
('Exotic Pets Emporium', 'Jason Miller', '555-444-3333', 'jason@example.com', FLOOR(RAND() * 50) + 1),
('Wildlife Wholesalers', 'Emily Harris', '222-333-4444', 'emily@example.com', FLOOR(RAND() * 50) + 1),
('Feathered Friends Suppliers', 'Daniel Taylor', '999-888-7777', 'daniel@example.com', FLOOR(RAND() * 50) + 1),
('Hoof and Paw Distributors', 'Sophia Adams', '666-555-4444', 'sophia@example.com', FLOOR(RAND() * 50) + 1),
('Reptile Retreat Importers', 'Matthew Robinson', '111-222-3333', 'matthew@example.com', FLOOR(RAND() * 50) + 1),
('Aqua World Suppliers', 'Ava Turner', '888-777-6666', 'ava@example.com', FLOOR(RAND() * 50) + 1),
('Purr-fect Pets Retailers', 'William White', '444-555-6666', 'william@example.com', FLOOR(RAND() * 50) + 1),
('Livestock Haven Distributors', 'Chloe Brown', '777-888-9999', 'chloe@example.com', FLOOR(RAND() * 50) + 1),
('Winged Wonders Importers', 'Owen Thomas', '555-444-3333', 'owen@example.com', FLOOR(RAND() * 50) + 1),
('Fur and Feather Supplies', 'Lily Miller', '222-333-4444', 'lily@example.com', FLOOR(RAND() * 50) + 1),
('Scale and Tail Distributors', 'Jackson Harris', '999-888-7777', 'jackson@example.com', FLOOR(RAND() * 50) + 1),
('Avian Allies Importers', 'Madison Adams', '666-555-4444', 'madison@example.com', FLOOR(RAND() * 50) + 1),
('Fluffy and Fins Retailers', 'Logan Robinson', '111-222-3333', 'logan@example.com', FLOOR(RAND() * 50) + 1),
('Wild Companions Suppliers', 'Abigail Turner', '888-777-6666', 'abigail@example.com', FLOOR(RAND() * 50) + 1),
('Barnyard Buddies Distributors', 'Gabriel White', '444-555-6666', 'gabriel@example.com', FLOOR(RAND() * 50) + 1),
('Aquatic Associates Importers', 'Emma Brown', '777-888-9999', 'emma@example.com', FLOOR(RAND() * 50) + 1),
('Feathery Friends Retailers', 'Carter Thomas', '555-444-3333', 'carter@example.com', FLOOR(RAND() * 50) + 1),
('Small Paws and Claws Suppliers', 'Scarlett Miller', '222-333-4444', 'scarlett@example.com', FLOOR(RAND() * 50) + 1),
('Finned and Furry Imports', 'Aiden Harris', '999-888-7777', 'aiden@example.com', FLOOR(RAND() * 50) + 1),
('Winged Wonders Retailers', 'Zoe Adams', '666-555-4444', 'zoe@example.com', FLOOR(RAND() * 50) + 1),
('Pet Paradise Suppliers', 'Brayden Robinson', '111-222-3333', 'brayden@example.com', FLOOR(RAND() * 50) + 1),
('Reptile Retreat Retailers', 'Nora Turner', '888-777-6666', 'nora@example.com', FLOOR(RAND() * 50) + 1),
('Furry Friends Wholesalers', 'Eli White', '444-555-6666', 'eli@example.com', FLOOR(RAND() * 50) + 1),
('Purr-fect Partners Imports', 'Hannah Brown', '777-888-9999', 'hannah@example.com', FLOOR(RAND() * 50) + 1),
('Feathered Companions Retailers', 'Liam Thomas', '555-444-3333', 'liam@example.com', FLOOR(RAND() * 50) + 1),
('Barnyard Buddies Suppliers', 'Grace Miller', '222-333-4444', 'grace@example.com', FLOOR(RAND() * 50) + 1),
('Scales and Tails Wholesalers', 'Mason Harris', '999-888-7777', 'mason@example.com', FLOOR(RAND() * 50) + 1),
('Fur and Fins Imports', 'Addison Adams', '666-555-4444', 'addison@example.com', FLOOR(RAND() * 50) + 1),
('Wild Companions Retailers', 'Evelyn Robinson', '111-222-3333', 'evelyn@example.com', FLOOR(RAND() * 50) + 1);

-- Insert records into pet_supplier_relationship for pet and supplier associations
INSERT INTO pet_supplier_relationship (pet_id, supplier_id) VALUES
-- For Cats
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1),
-- For Dogs
(6, 2), (7, 2), (8, 2), (9, 2), (10, 2),
-- For Cows
(11, 3), (12, 3), (13, 3), (14, 3), (15, 3),
-- For Horses
(16, 4), (17, 4), (18, 4), (19, 4), (20, 4),
-- For Donkeys
(21, 5), (22, 5), (23, 5), (24, 5), (25, 5),
-- For Rabbits
(26, 6), (27, 6), (28, 6), (29, 6), (30, 6),
-- For Parrots
(31, 7), (32, 7), (33, 7), (34, 7), (35, 7),
-- For Rats
(36, 8), (37, 8), (38, 8), (39, 8), (40, 8),
-- For Pigs
(41, 9), (42, 9), (43, 9), (44, 9), (45, 9),
-- For Turtles
(46, 10), (47, 10), (48, 10), (49, 10), (50, 10);

-- Insert records into employees for animal care staff
INSERT INTO employees (first_name, last_name, position, hire_date, phone_number, email, address_id) VALUES
('John', 'Smith', 'Veterinarian', '2022-01-01', '555-1234', 'john.smith@example.com', FLOOR(RAND() * 50) + 1),
('Emily', 'Johnson', 'Veterinary Technician', '2022-02-15', '555-5678', 'emily.johnson@example.com', FLOOR(RAND() * 50) + 1),
('David', 'Williams', 'Veterinary Assistant', '2022-03-20', '555-9012', 'david.williams@example.com', FLOOR(RAND() * 50) + 1),
('Jennifer', 'Davis', 'Manager', '2021-12-01', '555-3456', 'jennifer.davis@example.com', FLOOR(RAND() * 50) + 1),
('Michael', 'Brown', 'Assistant Manager', '2022-04-10', '555-7890', 'michael.brown@example.com', FLOOR(RAND() * 50) + 1),
('Emma', 'Miller', 'Pet Groomer', '2022-05-15', '555-2345', 'emma.miller@example.com', FLOOR(RAND() * 50) + 1),
('Daniel', 'Jones', 'Grooming Assistant', '2022-06-20', '555-6789', 'daniel.jones@example.com', FLOOR(RAND() * 50) + 1),
('Sophia', 'Garcia', 'Administrative Assistant', '2022-07-01', '555-0123', 'sophia.garcia@example.com', FLOOR(RAND() * 50) + 1),
('Matthew', 'Martinez', 'Office Manager', '2022-08-15', '555-4567', 'matthew.martinez@example.com', FLOOR(RAND() * 50) + 1),
('Olivia', 'Smith', 'Animal Caretaker', '2022-09-20', '555-8901', 'olivia.smith@example.com', FLOOR(RAND() * 50) + 1),
('Ethan', 'Taylor', 'Animal Care Specialist', '2022-10-01', '555-3456', 'ethan.taylor@example.com', FLOOR(RAND() * 50) + 1),
('Ava', 'Wilson', 'Zookeeper', '2022-11-15', '555-7890', 'ava.wilson@example.com', FLOOR(RAND() * 50) + 1),
('Noah', 'Anderson', 'Security Officer', '2022-12-20', '555-1234', 'noah.anderson@example.com', FLOOR(RAND() * 50) + 1),
('Isabella', 'Thomas', 'Security Supervisor', '2023-01-01', '555-5678', 'isabella.thomas@example.com', FLOOR(RAND() * 50) + 1),
('Mason', 'Hernandez', 'Animal Nutritionist', '2023-02-15', '555-9012', 'mason.hernandez@example.com', FLOOR(RAND() * 50) + 1),
('Amelia', 'Young', 'Dietary Assistant', '2023-03-20', '555-2345', 'amelia.young@example.com', FLOOR(RAND() * 50) + 1),
('Logan', 'Scott', 'Animal Behaviorist', '2023-04-10', '555-6789', 'logan.scott@example.com', FLOOR(RAND() * 50) + 1),
('Abigail', 'King', 'Behavioral Trainer', '2023-05-15', '555-0123', 'abigail.king@example.com', FLOOR(RAND() * 50) + 1),
('Jackson', 'Evans', 'Customer Service Representative', '2023-06-20', '555-4567', 'jackson.evans@example.com', FLOOR(RAND() * 50) + 1),
('Avery', 'Baker', 'Customer Care Specialist', '2023-07-01', '555-8901', 'avery.baker@example.com', FLOOR(RAND() * 50) + 1);

-- Insert records into employee_pet_relationship
INSERT INTO employee_pet_relationship (employee_id, pet_id) VALUES
-- Veterinary Staff
(1, 1),  -- John Smith takes care of a cat
(2, 2),  -- Emily Johnson is a vet tech for a dog
(3, 3),  -- David Williams assists in grooming a cow
-- Management
(4, 4),  -- Jennifer Davis manages a horse
(5, 5),  -- Michael Brown is assistant manager for a donkey
-- Grooming Staff
(6, 6),  -- Emma Miller grooms a rabbit
(7, 7),  -- Daniel Jones assists in grooming a parrot
-- Administrative Staff
(8, 8),  -- Sophia Garcia handles administrative work for rats
(9, 9),  -- Matthew Martinez is the office manager for pigs
-- Animal Caretakers
(10, 10),  -- Olivia Smith takes care of a turtle
(11, 11),  -- Ethan Taylor is an animal care specialist for a cat
(12, 12),  -- Ava Wilson is a zookeeper for a dog
-- Security
(13, 13),  -- Noah Anderson provides security for a cow
(14, 14),  -- Isabella Thomas is a security supervisor for a horse
-- Nutritionist
(15, 15),  -- Mason Hernandez is an animal nutritionist for a donkey
(16, 16),  -- Amelia Young assists in dietary needs for a rabbit
-- Animal Behaviorist
(17, 17),  -- Logan Scott focuses on the behavior of a parrot
(18, 18),  -- Abigail King is a behavioral trainer for rats
-- Customer Service
(19, 19),  -- Jackson Evans handles customer service for a pig
(20, 20),  -- Avery Baker is a customer care specialist for a turtle
-- Additional Records
(1, 21),  -- John Smith takes care of another cat
(2, 22),  -- Emily Johnson is a vet tech for another dog
(3, 23),  -- David Williams assists in grooming another cow
(4, 24),  -- Jennifer Davis manages another horse
(5, 25),  -- Michael Brown is assistant manager for another donkey
(6, 26),  -- Emma Miller grooms another rabbit
(7, 27),  -- Daniel Jones assists in grooming another parrot
(8, 28),  -- Sophia Garcia handles administrative work for another rat
(9, 29),  -- Matthew Martinez is the office manager for another pig
(10, 30); -- Olivia Smith takes care of another turtle

SELECT * FROM petshop.addresses;
SELECT * FROM petshop.customers;
SELECT * FROM petshop.employees;
SELECT * FROM petshop.grooming_services;
SELECT * FROM petshop.pet_categories;
SELECT * FROM petshop.pet_food;
SELECT * FROM petshop.pets;
SELECT * FROM petshop.suppliers;
SELECT * FROM petshop.transactions;
SELECT * FROM petshop.vaccinations;
SELECT * FROM petshop.employee_pet_relationship;
SELECT * FROM petshop.pet_food_relationship;
SELECT * FROM petshop.pet_grooming_relationship;
SELECT * FROM petshop.pet_supplier_relationship;
SELECT * FROM petshop.pet_vaccination_relationship;
