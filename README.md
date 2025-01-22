## API Reference

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