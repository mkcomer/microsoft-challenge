#python 
import pandas as pd
import psycopg2
from psycopg2 import Error
from sqlalchemy import create_engine



# Prepare DataFrames from CSVs before insert into postgres DB 
# For the purposes of quickly testing this API - we are only inserting a subset of the Jan taxi data
# Prepare Borough Cab Zone CSV
borough_file = "taxi+_zone_lookup.csv"
borough_df = pd.read_csv(borough_file)
# Make all columns standardized lowercase so we don't run into any column key issues with hibernate
borough_df.columns= borough_df.columns.str.strip().str.lower()
# TODO - Make zone all lowercase with no spaces to make query easier 

#Prepare yellow cab data 
yellow_file = "yellow.csv"
df1 = pd.read_csv(yellow_file)
df1.columns= df1.columns.str.strip().str.lower()
df1['type'] = "yellow"

# Prepare Green cab data 
green_file = "green.csv"
df2 = pd.read_csv(green_file)
df2.columns= df2.columns.str.strip().str.lower()
df2['type'] = "green"

# Prepare For Hire data 
for_hire_file = "forhirevehicle.csv"
df3 = pd.read_csv(for_hire_file)
df3.columns= df3.columns.str.strip().str.lower()
df3['type'] = "hire"

# Prepare full dataframe for insert to postgres
cab_df = df1.append(df2)
cab_final_df = cab_df.append(df3)
cab_final_df.reset_index()
print(cab_final_df)


# Configure psycopg2 to connect to local database
param_dic = {
    "host"      : "localhost",
    "database"  : "taxidatabase",
    "user"      : "postgres",
    "password"  : "postgres"
}

connect = "postgresql+psycopg2://%s:%s@%s:5432/%s" % (
    param_dic['user'],
    param_dic['password'],
    param_dic['host'],
    param_dic['database']
)

# SqlAlchemy Only to insert data
engine = create_engine(connect)

# Push borough data to local postgres in database = taxidatabase 
borough_df.to_sql('borough', con=engine,if_exists='append')
print("Borough data to_sql() completed via sqlalchemy")


# Push cab data to local postgres in database = taxidatabase 
cab_final_df.to_sql('taxi', con=engine,if_exists='append')
print("Cab taxi data to_sql() completed via sqlalchemy")
