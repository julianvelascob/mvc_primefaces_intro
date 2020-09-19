### Escuela Colombiana de Ingeniería

### Procesos de desarrollo de Software – PDSW

#### Frameworks Web MVC – Java Server Faces / Prime Faces

En este ejercicio, usted va a desarrollar una aplicación Web basada en
el marco JSF, y en una de sus implementaciones más usadas: [PrimeFaces]([*http://primefaces.org/*](http://primefaces.org/) ). Se trata de un
juego en línea para adivinar un número, en el que el ganador, si atina
en la primera oportunidad, recibe `$100.000`. Luego, por cada intento
fallido, el premio se reduce en `$10.000`.

1.  Construya un proyecto Maven, usando el arquetipo de
    aplicación Web estándar maven-archetype-webapp:

2.  Al proyecto Maven, debe agregarle las dependencias mas recientes de `javaee-api`, `jsf-api`, `jsf-impl`, `jstl` y Primefaces (en el archivo pom.xml).

3.  Agregar a la sección build:

     ```xml
     <plugins>
         <plugin>
             <groupId>org.apache.maven.plugins</groupId>
             <artifactId>maven-compiler-plugin</artifactId>
             <version>3.8.0</version>
             <configuration>
                 <source>1.8</source>
                 <target>1.8</target>
                 <compilerArguments>
                     <endorseddirs>${endorsed.dir}</endorseddirs>
                 </compilerArguments>
             </configuration>
         </plugin>
         <plugin>
             <groupId>org.apache.maven.plugins</groupId>
             <artifactId>maven-war-plugin</artifactId>
             <version>2.3</version>
             <configuration>
                 <failOnMissingWebXml>false</failOnMissingWebXml>
             </configuration>
         </plugin>
         <plugin>
             <groupId>org.apache.maven.plugins</groupId>
             <artifactId>maven-dependency-plugin</artifactId>
             <version>2.6</version>
             <executions>
                 <execution>
                     <phase>validate</phase>
                     <goals>
                         <goal>copy</goal>
                     </goals>
                     <configuration>
                         <outputDirectory>${endorsed.dir}</outputDirectory>
                         <silent>true</silent>
                         <artifactItems>
                             <artifactItem>
                                 <groupId>javax</groupId>
                                 <artifactId>javaee-endorsed-api</artifactId>
                                 <version>7.0</version>
                                 <type>jar</type>
                             </artifactItem>
                         </artifactItems>
                     </configuration>
                 </execution>
             </executions>
         </plugin>

         <!-- Tomcat embedded plugin. -->
         <plugin>
             <groupId>org.apache.tomcat.maven</groupId>
             <artifactId>tomcat7-maven-plugin</artifactId>
             <version>2.2</version>
             <configuration>
                 <port>8080</port>
                 <path>/</path>
             </configuration>
         </plugin>
     </plugins>
     ```

4.  Empaquete la aplicacion con maven: `mvn package` y ejecutela utilizando
    `mvn tomcat7:run`. Revise que la aplicacion este corriendo en el puerto 
    asignado utilizando el browser.

5.  Para que configure automáticamente el descriptor de despliegue de la
    aplicación (archivo web.xml), de manera que el *framework* JSF se active
    al inicio de la aplicación, en el archivo `web.xml` agregue la siguiente
    configuración:

     ```xml
    <servlet>
        <servlet-name>Faces Servlet</servlet-name>
        <servlet-class>javax.faces.webapp.FacesServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>Faces Servlet</servlet-name>
        <url-pattern>/faces/*</url-pattern>
    </servlet-mapping>
    <welcome-file-list>
        <welcome-file>faces/index.jsp</welcome-file>
    </welcome-file-list>
     ```

6.  Revise cada una de las configuraciones agregadas anteriormente para saber
    qué hacen y por qué se necesitan. Elimine las que no se necesiten.

7.  Ahora, va a crear un `Backing-Bean` de sesión, el cual, para cada
    usuario, mantendrá de lado del servidor las siguientes propiedades:

    a.  El número que actualmente debe adivinar (debe ser un número aleatorio).

    b.  El número de intentos realizados.

    c.  El premio acumulado hasta el momento.

    d.  El estado del juego, que sería una cadena de texto que indica si
        ya ganó o no, y si ganó de cuanto es el premio.

    Para hacer esto, cree una clase que tenga:
    * el constructor por defecto (sin parámetros);
    * los métodos `set/get` necesarios dependiendo si las 
      propiedades son de escritura o lectura; 
    * coloque las anotaciones:
        * `@ManagedBean`, incluyendo el nombre: `@ManagedBean(name = "guessBean")`.
        * `@ApplicationScoped`.

    A la implementación de esta clase, agregue los siguientes métodos:
    * `guess`: Debe recibir un intento de adivinanza y realizar la lógica para saber si se adivinó, de tal forma que se ajuste el valor del premio y/o actualice el estado del juego.
    * `restart`: Debe volver a iniciar el juego (inicializar de nuevo el número a adivinar, y restaurar el premio a su valor original).

8.  Cree una página XHTML, de nombre `guess.xhtml` (debe quedar en la ruta `src/main/webapp`). Revise en la [página 13 del manual de PrimeFaces](http://www.primefaces.org/docs/guide/primefaces_user_guide_5_2.pdf), qué espacios de nombres XML requiere una página de PrimeFaces, y cual es la estructura básica de la misma.

9.  Con base en lo anterior, agregue un formulario con identificador `guess_form` con el siguiente contenido básico:

     ```xml
    <h:body>
      <h:form id="guess_form">

      </h:form>
    </h:body>
     ```

10.  Al formulario, agregue:

     a. Un elemento de tipo `<p:outputLabel>` para el número que se debe adivinar,
        sin embargo, este elemento se debe ocultar. Para ocultarlo, se puede agregar
        el estilo `display: none;` al elemento. Una forma de hacerlo es por medio de
        la propiedad `style`.
         * En una aplicacion real, no se debería tener este elemento, solo se crea
           con el fin de simplificar una prueba futura.

     b. Un elemento `<p:inputText>` para que el usuario ingrese su número.

     c. Un elemento de tipo `<p:outputLabel>` para mostrar el número
        de intentos realizados.

     d. Un elemento de tipo `<p:outputLabel>` para mostrar el estado
        del juego.

     e. Un elemento de tipo `<p:outputLabel>` para mostrar en cuanto va el premio.

        Y asocie dichos elementos al BackingBean de sesión a través de su propiedad `value`, y usando como referencia el nombre asignado:

          ```java
          value="#{guessBean.nombrePropiedad}"
          ```

11.  Al formulario, agregue dos botones de tipo `<p:commandButton>`,
    uno para enviar el número ingresado y ver si se _atinó_, y otro para
    reiniciar el juego.

     a.  El botón de _envío de adivinanza_ debe tener asociado a su
         propiedad `update` el nombre del formulario en el que se
         agregaron los campos antes descritos, de manera que al hacer
         clic, se ejecute un ciclo de JSF y se _refresque_ la vista.

     b. Debe tener también una propiedad `actionListener` con la cual
         se le indicará que, al hacer clic, se ejecutará el método 
         `guess`, creado en el backing-bean de sesión:

         ```xml
         <p:commandButton update="guess_form" actionListener="#{guessBean.guess}">...
         ```

     b.  El botón de reiniciar juego tendrá las mismas propiedades de
         `update` y `actionListener` del otro con el valor correspondiente:

         ```xml
         <p:commandButton update="…" actionListener="…">
	 ```

12.  Para verificar el funcionamiento de la aplicación, agregue el plugin
    tomcat-runner dentro de los plugins de la fase de
    construcción (build). Tenga en cuenta que en la configuración del
    plugin se indica bajo que ruta quedará la aplicación:

     ```xml
     <plugin>
         <groupId>org.apache.tomcat.maven</groupId>
         <artifactId>tomcat7-maven-plugin</artifactId>
         <version>2.2</version>
         <configuration>                    
             <path>/</path>
         </configuration>
     </plugin>            
     ```

13.  Una vez hecho esto, desde la terminal, en el directorio del
     proyecto, ejecute:

     a.  mvn package

     b.  mvn tomcat7:run

     Si no hay errores, la aplicación debería quedar accesible en la URL: [http://localhost:8080/faces/guess.xhtml](http://localhost:8080/faces/guess.xhtml)

14.  Si todo funcionó correctamente, realice las siguientes pruebas:

     a.  Abra la aplicación en un explorador. Realice algunas pruebas con el
         juego e intente adivinar el número.

     b.  Abra la aplicación en dos computadores diferentes. Si no dispone
         de uno, hágalo en dos navegadores diferentes (por ejemplo Chrome
         y Firefox; incluso se puede en un único navegador usando una ventana
         normal y una ventana de incógnito / privada). Haga cinco intentos
         en uno, y luego un intento en el otro. ¿Qué valor tiene cada uno?

     c.  Aborte el proceso de Tomcat-runner haciendo Ctrl+C en la consola,
         y modifique el código del backing-bean de manera que use la
         anotación @SessionScoped en lugar de @ApplicationScoped.
         Reinicie la aplicación y repita el ejercicio anterior.
         * ¿Coinciden los valores del premio?.
         * Dado la anterior, ¿Cuál es la diferencia entre los backing-beans de sesión y los de aplicación?

     d.  Por medio de las herramientas de desarrollador del explorador:
         * Ubique el código HTML generado por el servidor.
         * Busque el elemento oculto, que contiene el número generado aleatoriamente.
         * En la sección de estilos, deshabilite el estilo que oculta el elemento para que sea visible.
         * Observe el cambio en la página, cada vez que se realiza un cambio en el estilo.
         * Revise qué otros estilos se pueden agregar a los diferentes elementos y qué efecto tienen en la visualización de la página.
         * Actualice la página. Los cambios de estilos realizados desaparecen, pues se realizaron únicamente en la visualización, la respuesta del servidor sigue siendo la misma, ya que el contenido de los archivos allí almacenados no se ha modificado.
         * Revise qué otros cambios se pueden realizar y qué otra información se puede obtener de las herramientas de desarrollador.

15.  Para facilitar los intentos del usuario, se agregará una lista de los últimos intentos fallidos realizados:

     a.  Agregue en el `Backing-Bean`, una propiedad que contenga una lista de intentados realizados.

     b.  Agregue cada intento a la lista, cuando se ejecute el método `guess`.

     c.  Cuando se reinicie el juego, limpie el contenido de la lista.

     d.  Busque cómo agregar una tabla a la página, cuyo contenido sea los últimos intentos realizados.
