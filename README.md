## API Reference - Items

#### Получить список товаров

`
  GET /items
`

| Параметры  | Тип       | Description         |
|:-----------|:----------|:--------------------|
| `name`     | `String`  | Наименование товара |
| `minPrice` | `Double`  | Минимальная цена    |
| `maxPrice` | `Double`  | Максимальная цена   |
| `inStock`  | `Boolean` | Наличие             |
| `sortBy`   | `String`  | Тип сортировки      |
| `limit`    | `Integer` | Лимит на выборку    |
| `order`    | `String`  | Порядок сортировки  |

#### Получить один товар

`
  GET /items/${id}
`

#### Создать товар

`
  POST /items
`
#### Обновить товар

`
  PUT /items/${id}
`

#### Удалить товар

`
  DELETE /items/${id}
`

## API Reference - Deliveries, Sales 

#### Получить список документов

`
  GET /deliveries  
`
`
  GET /sales  
`

#### Получить один документ

`
  GET /deliveries/${id}
`
`
  GET /sales/${id}
`

#### Создать документ

`
  POST /deliveries
`
`
  POST /sales
`
#### Обновить документ

`
  PUT /deliveries/${id}
`
`
  PUT /sales/${id}
`
#### Удалить документ

`
  DELETE /deliveries/${id}
`
`
  DELETE /sales/${id}
`