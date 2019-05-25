#Fletcher
#This script will download the first x Pokemon sprites by their Pokedex number
import urllib.request

root_url = "https://github.com/PokeAPI/sprites/raw/master/sprites/pokemon/";
file_type = ".png";
max = 151;
for i in range(1, max+1):
    urllib.request.urlretrieve(root_url + str(i) + file_type, "../sprites/" + str(i) + file_type);