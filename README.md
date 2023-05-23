# TSF (Teikoku Scheduling Framewok) 
The TSF that started as a tool for simulating load balancing and scheduling problems. The project started at the University of Dortmund Germany, our branch has continously added new functionality, such as: distributed information system, workflow scheduling sypport, and energy conservation.  

The project contains the following subdirectories
- doc, contains a document that describes the architecture of the framework
- tools, constains mathlab functions and tools to prepare results
- src, the source of the projects. It contains two mains branches de and mx. de contains core simulation functionality, while mx contains workflow suppor and the hierarchical energy model.
-------------------------------------------
Este proyecto constituye un derivado del trabajo original de nuestro estimado profesor, enriqueciéndolo y ampliándolo al implementar la técnica de hashing consistente en este algoritmo. Al embarcarnos en esta empresa, abrazamos la oportunidad de magnificar el legado académico, infundiendo nuevas ideas y mejorando su eficacia para su aplicación práctica.
| Clave de la clase   |     ICC006      |
|:----------|:-------------|
| **Título del proyecto** |  Consistent y Rendezvous Hashing |
| **Miembros de equipo** | 35994 - Sebastian Garcia<br>32366 - Luis Bernardo Bremer Ortega<br>32954 - John Brandon Nemeth Rodriguez<br>35592 - Rodrigo Rosas Fernandez|


# Introducción:
----
Este reporte se enfocará en el estudio detallado del algoritmo de Consistent Hashing. Este algoritmo, introducido por primera vez por Karger et al. en 1997, se ha convertido en una solución clave para el equilibrio de carga, la tolerancia a fallos y la escalabilidad en sistemas distribuidos. Su aplicación incluye sistemas de almacenamiento en caché, bases de datos distribuidas y balanceo de carga en servidores web.
En este reporte, se discutirán los fundamentos y las evoluciones del algoritmo de Consistent Hashing, incluyendo su pseudocódigo, análisis asintótico, casos de estudio, características del conjunto de datos, implementación, métricas, resultados y limitaciones. Se mostrarán las iteraciones de cada implementación, incluyendo Linear Probing.
Además, se examinarán otras heurísticas y enfoques relacionados que se han desarrollado para abordar los desafíos y demandas de la distribución de datos y la escalabilidad en sistemas modernos. Con este análisis exhaustivo, se espera brindar una visión actualizada y completa del impacto del algoritmo de Consistent Hashing en el campo de la informática distribuida.

# Análisis asintótico de Consistent Hashing:
----
El enfoque principal es en cómo cambia la distribución de datos y la cantidad de datos que deben moverse cuando se agregan o eliminan nodos del sistema. Existen dos aspectos clave en Consistent Hashing.

**Load Balancing:**

El algoritmo de Consistent Hashing busca distribuir uniformemente los datos entre los nodos. En teoría, si se utiliza una función de hash que produce una distribución uniforme y se emplean nodos virtuales, el balance de carga puede acercarse a un estado ideal. En un sistema con 'n' nodos y 'K' claves (Keys), cada nodo contendría aproximadamente K/n claves. Sin embargo, en la práctica, pueden haber variaciones debido a la no uniformidad en la distribución de las claves y los nodos. La utilización de nodos virtuales puede reducir significativamente este desequilibrio.

**Minimal Key Remapping:**

Uno de los principales objetivos de Consistent Hashing es minimizar el movimiento de datos cuando se agregan o eliminan nodos. Si se utiliza una función de hash uniforme y se emplean nodos virtuales, el movimiento de datos se acerca a un mínimo teórico. En un sistema con 'n' nodos, cuando se agrega o elimina un nodo, solo se requiere reasignar aproximadamente 1/n de las claves en promedio. Esto es un gran avance en comparación con los enfoques de hashing tradicionales, donde el movimiento de datos puede ser mucho mayor.
