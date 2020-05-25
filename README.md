# Traductor de código morse 


API REST MORSE


#### Definición del problema

Debemos poder traducir código morse a alfanumerico y viciversa. Ademas debemos poder traducir un conjunto de bits a código morse.


#### Solución planteada
La solución para traducir código morse a alfanumerico y viciversa es utilizar dos mapas en memoria. Uno de los mapas tiene el siguiente formato:

Map("código morse"-> Alfanumerico) y el otro mapa es Map("alfa"->"código morse")

Para traducir los bits a código morse se aplica la siguiente lógica:

Para saber si es un "." o "-" sacamos el promedio de 1s (unos) que aparecen en el mensaje. Luego contamos la cantidad de 1s seguidos que hay en el mensaje hasta encontrar un cero. Si la cantidad de 1s es mayor al promedio entonces es un "-" si no es un "." 


Para resolver los espacios de letras y palabras hacemos algo similar obtenemos el promedio de 0s (ceros) enviados y luego tomamos una decisión.

Para diferenciar entre espacio caracter morse, espacio entre letras y espacio entre palabras se toma la siguiente decisión

Si la cantidad de 0s es menor o igual al promedio de 0s significa un espacio entre caracter morse.
Si la cantidad de 0s es menor o igual al ((promedio * 2) + 1) significa un espacio entre letras.
Si no se dan ninguna de las dos condiciones mencionadas, significa que es un espacio de palabras.

Asumimos que un espacio de palabras es mayor igual a uno o mas espacios de letras.

#### Consideraciones

Una consideracion que tuve es que en el ejemplo que ponen en el examen es un conjunto de bits asi:

000000001101101100111000001111110001111110011111100000001110111111110111011100000001100011111100000111111001111110000000110000110111111110111011100000011011100000000000 en alfanumerico es holameli
 
Vemos que es un código morse que no tiene ningun espacio de palabras, quizas lo correcto seria que venga un espacio de palabras asi:

00000000110110110011100001111110001111110011111100000001110111111110111011100000001100011111100000000000000111111001111110000000110000110111111110111011100000011011100000000000 en alfanumerico es hola meli

### Requerimientos para el Build

* Java 1.8 o superior
* [sbt]


Como correr la app 

```sh
$ sbt run
```

### Host Server

La api esta disponible con sus test en swagger para facilitar su [pruebas]

##### Endpoints hosteados

```sh
$ curl 'https://morsemeli.herokuapp.com/translate/2text' -H 'Content-Type: application/json' --data-binary $'{"text": ".... --- .-.. .-"}'

$ curl 'https://morsemeli.herokuapp.com/translate/2morse' -H 'Content-Type: application/json' --data-binary $'{"text": "hola"}' 

$ curl 'https://morsemeli.herokuapp.com/translate/binaryToMorse' -H 'Content-Type: application/json' --data-binary $'{"text":"00000000110110110011100001111110001111110011111100000001110111111110111011100000001100011111100000000000000111111001111110000000110000110111111110111011100000011011100000000000"}'

$ curl -X POST "https://morsemeli.herokuapp.com/translate/smart" -H "accept: application/json" -H "Content-Type: application/json" -d "{"text\":"hola","source":"alfa","target":"morse"}"

```  

### Localhost


API REST MORSE dispone de la interface Swagger (disponibilizamos los endpoint de la api rest) para poder realizar los test.

La misma se encuentra en la siguiente url [test local]



##### Endpoints

```sh
$ curl 'http://localhost:9000/translate/2text' -H 'Content-Type: application/json' --data-binary $'{"text": ".... --- .-.. .-"}'

$ curl 'http://localhost:9000/translate/2morse' -H 'Content-Type: application/json' --data-binary $'{"text": "hola"}' 

$ curl 'http://localhost:9000/translate/binaryToMorse' -H 'Content-Type: application/json' --data-binary $'{"text":"00000000110110110011100001111110001111110011111100000001110111111110111011100000001100011111100000000000000111111001111110000000110000110111111110111011100000011011100000000000"}'
```


### Cobertura

Podemos ver el reporte de cobertura en conf/resources/scoverage-report/index.html o podemos realizar el reporte de cobertura del código debemos habilitando coverage en el file build.sbt:


```sh
coverageEnabled := true
```
Luego ejecutar

```sh
$ sbt clean coverage test coverageReport
```
Ahora tenemos el reporte de cobertura en el directorio target/scala-2.12/scoverage-report



   [sbt]: <https://www.scala-sbt.org/index.html>

[aqui]: <https://morsemeli.herokuapp.com/>

[pruebas]: <https://morsemeli.herokuapp.com/docs/swagger-ui/index.html?url=/assets/swagger.json#/routes/translate2Human>

[test local]: <http://localhost:9000/docs/swagger-ui/index.html?url=/assets/swagger.json#/routes/translate2Human> 