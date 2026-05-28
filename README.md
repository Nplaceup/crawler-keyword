# keyword-crawler


네이버 검색광고 API를 통해 **키워드 검색량 및 연관 키워드**를 크롤링합니다.

---

## 결과값

**`KeywordCallbackRequest`**

| 필드 | 설명 |
|---|---|
| `keyword` | 검색 키워드 |
| `crawlDate` | 크롤링 날짜 |

### `data.keywordList[]` 상세

| 필드 | 설명 |
|---|---|
| `relKeyword` | 연관 키워드명 |
| `monthlyPcQcCnt` | 월간 PC 검색량 |
| `monthlyMobileQcCnt` | 월간 모바일 검색량 |
| `totalSearchVolume` | PC + 모바일 합산 검색량 |
| `compIdx` | 경쟁 지수 (`높음` / `보통` / `낮음`) |

---

## 저장 대상

| 데이터 | 설명 |
|---|---|
| `KeywordSearchVolumes` 저장 | 입력 키워드 본인의 PC/모바일/합산 검색량 |
| `KeywordRelated` 저장 | 연관 키워드 전체 목록 및 경쟁 지수 |
