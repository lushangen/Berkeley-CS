CREATE TABLE parents AS
  SELECT "abraham" AS parent, "barack" AS child UNION
  SELECT "abraham"          , "clinton"         UNION
  SELECT "delano"           , "herbert"         UNION
  SELECT "fillmore"         , "abraham"         UNION
  SELECT "fillmore"         , "delano"          UNION
  SELECT "fillmore"         , "grover"          UNION
  SELECT "eisenhower"       , "fillmore";

CREATE TABLE dogs AS
  SELECT "abraham" AS name, "long" AS fur, 26 AS height UNION
  SELECT "barack"         , "short"      , 52           UNION
  SELECT "clinton"        , "long"       , 47           UNION
  SELECT "delano"         , "long"       , 46           UNION
  SELECT "eisenhower"     , "short"      , 35           UNION
  SELECT "fillmore"       , "curly"      , 32           UNION
  SELECT "grover"         , "short"      , 28           UNION
  SELECT "herbert"        , "curly"      , 31;

CREATE TABLE sizes AS
  SELECT "toy" AS size, 24 AS min, 28 AS max UNION
  SELECT "mini"       , 28       , 35        UNION
  SELECT "medium"     , 35       , 45        UNION
  SELECT "standard"   , 45       , 60;

-------------------------------------------------------------
-- PLEASE DO NOT CHANGE ANY SQL STATEMENTS ABOVE THIS LINE --
-------------------------------------------------------------

-- The size of each dog
CREATE TABLE size_of_dogs AS
  SELECT name, size from dogs, sizes
    where min < height and max >= height;

-- All dogs with parents ordered by decreasing height of their parent
CREATE TABLE by_parent_height AS
  SELECT child from parents,
    dogs as a where a.name = parent
    order by -a.height;

-- Filling out this helper table is optional
CREATE TABLE siblings AS
  SELECT "REPLACE THIS LINE WITH YOUR SOLUTION";

-- Sentences about siblings that are the same size
CREATE TABLE sentences AS
  SELECT s1.name || " and " || s2.name || " are " || s1.size ||" siblings"
    from size_of_dogs as s1, size_of_dogs as s2, parents as a, parents as b
      where s1.size = s2.size and a.parent = b.parent and s1.name < s2.name and a.child = s1.name and b.child = s2.name
        order by s1.name;

-- Ways to stack 4 dogs to a height of at least 170, ordered by total height
CREATE TABLE stacks_helper(dogs, stack_height, last_height);
  insert into stacks_helper(dogs, stack_height, last_height)
    select d1.name || ', ' || d2.name || ', ' || d3.name || ', ' || d4.name, d1.height+d2.height+d3.height+d4.height, d1.height
      from dogs as d1, dogs as d2, dogs as d3, dogs as d4
        where d4.height > d3.height and d3.height > d2.height and d2.height > d1.height;
-- Add your INSERT INTOs here


CREATE TABLE stacks AS
  SELECT dogs, stack_height
    from stacks_helper
      where stack_height > 170
        order by stack_height;
