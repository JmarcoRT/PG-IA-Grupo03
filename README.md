# Proyecto Grupal de Inteligencia Artificial

**Asignatura:** Inteligencia Artificial

**Semestre:** 2026-I
**Docente:** Prof. Juan Gamarra Moreno

## Integrantes

- Bautista De la Cruz Claudia Daniela
- Carrascal Castro Priscila Maria
- Ccahuana Quiñones Judith Valeria
- Gil Sixi Alberto Luis
- Medrano Ayma Nikol Arlet
- Rosales Trinidad Jeanmarco Miguel
- Saire Tello Fernando José

## Descripción del proyecto

Este repositorio contiene los seis informes del Proyecto Grupal, organizados en dos bloques:

- **Bloque A (Java):** algoritmos de búsqueda en el espacio de estados (Informe 1) y juegos adversariales con poda Alfa-Beta (Informe 2).
- **Bloque B (Python):** modelos de aprendizaje automático supervisado y no supervisado (Informes 3 a 6), articulados en torno a una sola empresa ficticia y un mismo dataset sintético.

## Caso del Bloque B: AndinaSense

AndinaSense es una empresa ficticia de agricultura de precisión que brinda servicios de monitoreo a agroexportadoras de arándano en los valles de la costa norte del Perú (La Libertad, Lambayeque e Ica). Mediante sensores instalados en las parcelas, registra las condiciones de suelo, clima y manejo de cada una. Los cuatro informes del Bloque B usan el mismo dataset:

- **Informe 3 (Regresión):** predice el rendimiento de una parcela (rendimiento_kg_ha).
- **Informe 4 (Clasificación):** predice la calidad de cosecha (calidad_cosecha: Baja, Media, Alta).
- **Informe 5 (Agrupamiento):** segmenta las parcelas en perfiles agroecológicos con K-Means y DBSCAN.
- **Informe 6 (Reducción de dimensionalidad):** aplica PCA y t-SNE para visualización e interpretación.

## Dataset sintético

El dataset andinasense_parcelas.csv es propio del grupo y fue generado de forma reproducible con semilla fija (SEED = 42). Contiene 1500 registros y 13 variables, con mezcla de numéricas y categóricas, una variable objetivo continua (rendimiento_kg_ha) y una categórica (calidad_cosecha). Presenta correlaciones, ruido, valores atípicos y nulos intencionales para exigir preprocesamiento.

El proceso generador completo está documentado en data/PG_IA_Grupo03_GeneracionDataset.ipynb. Para reproducir el dataset desde cero, ejecutar ese notebook con Restart & Run All; produce un andinasense_parcelas.csv idéntico al incluido en este repositorio.

## Estructura del repositorio

```
proyecto-ia-grupo03/
├── README.md
├── autoevaluacion/
├── data/
│   ├── andinasense_parcelas.csv
│   └── PG_IA_Grupo03_GeneracionDataset.ipynb
├── exposicion/
│   ├── enlace_video.txt
│   └── orden_participacion.txt
├── java/
│   ├── Informe1_Busqueda/
│   └── Informe2_AlfaBeta/
├── notebooks/
│   ├── andinasense_parcelas.csv
│   ├── PG_IA_Grupo03_Informe3_Regresion.ipynb
│   ├── PG_IA_Grupo03_Informe4_Clasificacion.ipynb
│   ├── PG_IA_Grupo03_Informe5_Agrupamiento.ipynb
│   └── PG_IA_Grupo03_Informe6_ReduccionDimensionalidad.ipynb
├── informes/
│   ├── PG_IA_Grupo03_Informe3_Regresion.html
│   ├── PG_IA_Grupo03_Informe4_Clasificacion.html
│   ├── PG_IA_Grupo03_Informe5_Agrupamiento.html
│   └── PG_IA_Grupo03_Informe6_ReduccionDimensionalidad.html
└── prompts/
    └── PG_IA_Grupo03_AnexoPrompts.pdf
```

El archivo andinasense_parcelas.csv se incluye también dentro de notebooks/ para que los notebooks se ejecuten sin ajustar rutas. La carpeta exposicion/ contiene el enlace al video y el orden de participación, y autoevaluacion/ contiene la autoevaluación del equipo.

## Cómo ejecutar el Bloque B

1. Abrir cualquiera de los notebooks de notebooks/ en Jupyter.
2. Ejecutar con Restart & Run All. El notebook carga andinasense_parcelas.csv desde su misma carpeta.
3. Los notebooks usan SEED = 42 y un flujo con Pipeline y ColumnTransformer para evitar fuga de datos.

Requisitos: Python 3, numpy, pandas, matplotlib, seaborn y scikit-learn.

## Cómo ejecutar el Bloque A

Los programas en Java de los Informes 1 y 2 están en java/. Cada carpeta contiene los archivos .java y el informe correspondiente. Compilar y ejecutar con un JDK estándar desde cada carpeta.

## Declaración de uso de IA

Durante el desarrollo del proyecto se utilizó IA generativa (Claude Opus 4.8) como apoyo para estructurar el modelado, generar el dataset sintético, depurar código e interpretar resultados. Todos los prompts empleados, junto con su objetivo, resultado y la validación realizada por el equipo, están documentados en prompts/PG_IA_Grupo03_AnexoPrompts.pdf. El equipo verificó, corrigió y comprendió todo el código y los resultados presentados.
