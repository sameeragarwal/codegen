/* 001 */ public Object generate(Object[] references) {
/* 002 */   return new GeneratedIterator(references);
/* 003 */ }
/* 004 */
/* 005 */ /** Codegened pipeline for:
/* 006 */ * TungstenAggregate(key=[k#51L], functions=[(sum(k#51L),mode=Final,isDistinct=false)], output=[k#51L,sum(k)#55L])
/* 007 */ +- INPUT
/* 008 */ */
/* 009 */ final class GeneratedIterator extends org.apache.spark.sql.execution.BufferedRowIterator {
/* 010 */   private Object[] references;
/* 011 */   private boolean agg_initAgg;
/* 012 */   private agg_GeneratedAggregateHashMap agg_aggregateHashMap;
/* 013 */   private java.util.Iterator<org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row> agg_genMapIter;
/* 014 */   private org.apache.spark.sql.execution.aggregate.TungstenAggregate agg_plan;
/* 015 */   private org.apache.spark.sql.execution.UnsafeFixedWidthAggregationMap agg_hashMap;
/* 016 */   private org.apache.spark.sql.execution.UnsafeKVExternalSorter agg_sorter;
/* 017 */   private org.apache.spark.unsafe.KVIterator agg_mapIter;
/* 018 */   private scala.collection.Iterator inputadapter_input;
/* 019 */   private UnsafeRow agg_result;
/* 020 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder agg_holder;
/* 021 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter agg_rowWriter;
/* 022 */   private UnsafeRow agg_result1;
/* 023 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder agg_holder1;
/* 024 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter agg_rowWriter1;
/* 025 */   private org.apache.spark.sql.execution.metric.LongSQLMetric wholestagecodegen_numOutputRows;
/* 026 */   private org.apache.spark.sql.execution.metric.LongSQLMetricValue wholestagecodegen_metricValue;
/* 027 */
/* 028 */   public GeneratedIterator(Object[] references) {
/* 029 */     this.references = references;
/* 030 */   }
/* 031 */
/* 032 */   public void init(int index, scala.collection.Iterator inputs[]) {
/* 033 */     partitionIndex = index;
/* 034 */     agg_initAgg = false;
/* 035 */     agg_aggregateHashMap = new agg_GeneratedAggregateHashMap();
/* 036 */
/* 037 */     this.agg_plan = (org.apache.spark.sql.execution.aggregate.TungstenAggregate) references[0];
/* 038 */
/* 039 */     inputadapter_input = inputs[0];
/* 040 */     agg_result = new UnsafeRow(1);
/* 041 */     this.agg_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(agg_result, 0);
/* 042 */     this.agg_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(agg_holder, 1);
/* 043 */     agg_result1 = new UnsafeRow(2);
/* 044 */     this.agg_holder1 = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(agg_result1, 0);
/* 045 */     this.agg_rowWriter1 = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(agg_holder1, 2);
/* 046 */     this.wholestagecodegen_numOutputRows = (org.apache.spark.sql.execution.metric.LongSQLMetric) references[1];
/* 047 */     wholestagecodegen_metricValue = (org.apache.spark.sql.execution.metric.LongSQLMetricValue) wholestagecodegen_numOutputRows.localValue();
/* 048 */   }
/* 049 */
/* 050 */   public class agg_GeneratedAggregateHashMap {
/* 051 */     public org.apache.spark.sql.execution.vectorized.ColumnarBatch batch;
/* 052 */     private int[] buckets;
/* 053 */     private int numBuckets;
/* 054 */     private int maxSteps;
/* 055 */     private int numRows = 0;
/* 056 */     private org.apache.spark.sql.types.StructType schema =
/* 057 */     new org.apache.spark.sql.types.StructType()
/* 058 */     .add("k", org.apache.spark.sql.types.DataTypes.LongType)
/* 059 */     .add("sum", org.apache.spark.sql.types.DataTypes.LongType);
/* 060 */
/* 061 */     public agg_GeneratedAggregateHashMap() {
/* 062 */       int DEFAULT_CAPACITY = 1 << 16;
/* 063 */       double DEFAULT_LOAD_FACTOR = 0.25;
/* 064 */       int DEFAULT_MAX_STEPS = 5;
/* 065 */       assert (DEFAULT_CAPACITY > 0 && ((DEFAULT_CAPACITY & (DEFAULT_CAPACITY - 1)) == 0));
/* 066 */       this.maxSteps = DEFAULT_MAX_STEPS;
/* 067 */       numBuckets = (int) (DEFAULT_CAPACITY / DEFAULT_LOAD_FACTOR);
/* 068 */       batch = org.apache.spark.sql.execution.vectorized.ColumnarBatch.allocate(schema,
/* 069 */         org.apache.spark.memory.MemoryMode.ON_HEAP, DEFAULT_CAPACITY);
/* 070 */       buckets = new int[numBuckets];
/* 071 */       java.util.Arrays.fill(buckets, -1);
/* 072 */     }
/* 073 */
/* 074 */     public org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row findOrInsert(long agg_key) {
/* 075 */       long h = hash(agg_key);
/* 076 */       int step = 0;
/* 077 */       int idx = (int) h & (numBuckets - 1);
/* 078 */       while (step < maxSteps) {
/* 079 */         // Return bucket index if it's either an empty slot or already contains the key
/* 080 */         if (buckets[idx] == -1) {
/* 081 */           batch.column(0).putLong(numRows, agg_key);
/* 082 */           batch.column(1).putLong(numRows, 0);
/* 083 */           buckets[idx] = numRows++;
/* 084 */           return batch.getRow(buckets[idx]);
/* 085 */         } else if (equals(idx, agg_key)) {
/* 086 */           return batch.getRow(buckets[idx]);
/* 087 */         }
/* 088 */         idx = (idx + 1) & (numBuckets - 1);
/* 089 */         step++;
/* 090 */       }
/* 091 */       // Didn't find it
/* 092 */       return null;
/* 093 */     }
/* 094 */
/* 095 */     private boolean equals(int idx, long agg_key) {
/* 096 */       return batch.column(0).getLong(buckets[idx]) == agg_key;
/* 097 */     }
/* 098 */
/* 099 */     // TODO: Improve this hash function
/* 100 */     private long hash(long agg_key) {
/* 101 */       return agg_key;
/* 102 */     }
/* 103 */
/* 104 */   }
/* 105 */
/* 106 */   private void agg_doAggregateWithKeys() throws java.io.IOException {
/* 107 */     agg_hashMap = agg_plan.createHashMap();
/* 108 */
/* 109 */     /*** PRODUCE: INPUT */
/* 110 */
/* 111 */     while (inputadapter_input.hasNext()) {
/* 112 */       InternalRow inputadapter_row = (InternalRow) inputadapter_input.next();
/* 113 */       /*** CONSUME: TungstenAggregate(key=[k#51L], functions=[(sum(k#51L),mode=Final,isDistinct=false)], output=[k#51L,sum(k)#55L]) */
/* 114 */       /* input[0, bigint] */
/* 115 */       long inputadapter_value = inputadapter_row.getLong(0);
/* 116 */       /* input[1, bigint] */
/* 117 */       boolean inputadapter_isNull1 = inputadapter_row.isNullAt(1);
/* 118 */       long inputadapter_value1 = inputadapter_isNull1 ? -1L : (inputadapter_row.getLong(1));
/* 119 */
/* 120 */       // generate grouping key
/* 121 */       agg_rowWriter.write(0, inputadapter_value);
/* 122 */       /* hash(input[0, bigint], 42) */
/* 123 */       int agg_value3 = 42;
/* 124 */
/* 125 */       agg_value3 = org.apache.spark.unsafe.hash.Murmur3_x86_32.hashLong(inputadapter_value, agg_value3);
/* 126 */       UnsafeRow agg_aggBuffer = null;
/* 127 */       org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row agg_aggregateRow = null;
/* 128 */       if (true) {
/* 129 */         agg_aggregateRow =
/* 130 */         agg_aggregateHashMap.findOrInsert(inputadapter_value);
/* 131 */         // try to get the buffer from hash map
/* 132 */         if (agg_aggregateRow == null) {
/* 133 */           agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 134 */         }
/* 135 */       }
/* 136 */       if (agg_aggregateRow == null && agg_aggBuffer == null) {
/* 137 */         if (agg_sorter == null) {
/* 138 */           agg_sorter = agg_hashMap.destructAndCreateExternalSorter();
/* 139 */         } else {
/* 140 */           agg_sorter.merge(agg_hashMap.destructAndCreateExternalSorter());
/* 141 */         }
/* 142 */
/* 143 */         // the hash map had be spilled, it should have enough memory now,
/* 144 */         // try  to allocate buffer again.
/* 145 */         agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 146 */         if (agg_aggBuffer == null) {
/* 147 */           // failed to allocate the first page
/* 148 */           throw new OutOfMemoryError("No enough memory for aggregation");
/* 149 */         }
/* 150 */       }
/* 151 */
/* 152 */       if (agg_aggregateRow != null) {
/* 153 */         // evaluate aggregate function
/* 154 */         /* coalesce((coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]), input[0, bigint]) */
/* 155 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]) */
/* 156 */         boolean agg_isNull5 = true;
/* 157 */         long agg_value6 = -1L;
/* 158 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 159 */         /* input[0, bigint] */
/* 160 */         boolean agg_isNull7 = agg_aggregateRow.isNullAt(0);
/* 161 */         long agg_value8 = agg_isNull7 ? -1L : (agg_aggregateRow.getLong(0));
/* 162 */         boolean agg_isNull6 = agg_isNull7;
/* 163 */         long agg_value7 = agg_value8;
/* 164 */
/* 165 */         if (agg_isNull6) {
/* 166 */           /* cast(0 as bigint) */
/* 167 */           boolean agg_isNull8 = false;
/* 168 */           long agg_value9 = -1L;
/* 169 */           if (!false) {
/* 170 */             agg_value9 = (long) 0;
/* 171 */           }
/* 172 */           if (!agg_isNull8) {
/* 173 */             agg_isNull6 = false;
/* 174 */             agg_value7 = agg_value9;
/* 175 */           }
/* 176 */         }
/* 177 */
/* 178 */         if (!inputadapter_isNull1) {
/* 179 */           agg_isNull5 = false; // resultCode could change nullability.
/* 180 */           agg_value6 = agg_value7 + inputadapter_value1;
/* 181 */
/* 182 */         }
/* 183 */         boolean agg_isNull4 = agg_isNull5;
/* 184 */         long agg_value5 = agg_value6;
/* 185 */
/* 186 */         if (agg_isNull4) {
/* 187 */           /* input[0, bigint] */
/* 188 */           boolean agg_isNull11 = agg_aggregateRow.isNullAt(0);
/* 189 */           long agg_value12 = agg_isNull11 ? -1L : (agg_aggregateRow.getLong(0));
/* 190 */           if (!agg_isNull11) {
/* 191 */             agg_isNull4 = false;
/* 192 */             agg_value5 = agg_value12;
/* 193 */           }
/* 194 */         }
/* 195 */         // update aggregate row
/* 196 */         if (!agg_isNull4) {
/* 197 */           agg_aggregateRow.setLong(1, agg_value5);
/* 198 */         } else {
/* 199 */           agg_aggregateRow.setNullAt(1);
/* 200 */         }
/* 201 */       } else {
/* 202 */         // evaluate aggregate function
/* 203 */         /* coalesce((coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]), input[0, bigint]) */
/* 204 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]) */
/* 205 */         boolean agg_isNull13 = true;
/* 206 */         long agg_value14 = -1L;
/* 207 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 208 */         /* input[0, bigint] */
/* 209 */         boolean agg_isNull15 = agg_aggBuffer.isNullAt(0);
/* 210 */         long agg_value16 = agg_isNull15 ? -1L : (agg_aggBuffer.getLong(0));
/* 211 */         boolean agg_isNull14 = agg_isNull15;
/* 212 */         long agg_value15 = agg_value16;
/* 213 */
/* 214 */         if (agg_isNull14) {
/* 215 */           /* cast(0 as bigint) */
/* 216 */           boolean agg_isNull16 = false;
/* 217 */           long agg_value17 = -1L;
/* 218 */           if (!false) {
/* 219 */             agg_value17 = (long) 0;
/* 220 */           }
/* 221 */           if (!agg_isNull16) {
/* 222 */             agg_isNull14 = false;
/* 223 */             agg_value15 = agg_value17;
/* 224 */           }
/* 225 */         }
/* 226 */
/* 227 */         if (!inputadapter_isNull1) {
/* 228 */           agg_isNull13 = false; // resultCode could change nullability.
/* 229 */           agg_value14 = agg_value15 + inputadapter_value1;
/* 230 */
/* 231 */         }
/* 232 */         boolean agg_isNull12 = agg_isNull13;
/* 233 */         long agg_value13 = agg_value14;
/* 234 */
/* 235 */         if (agg_isNull12) {
/* 236 */           /* input[0, bigint] */
/* 237 */           boolean agg_isNull19 = agg_aggBuffer.isNullAt(0);
/* 238 */           long agg_value20 = agg_isNull19 ? -1L : (agg_aggBuffer.getLong(0));
/* 239 */           if (!agg_isNull19) {
/* 240 */             agg_isNull12 = false;
/* 241 */             agg_value13 = agg_value20;
/* 242 */           }
/* 243 */         }
/* 244 */         // update aggregate buffer
/* 245 */         if (!agg_isNull12) {
/* 246 */           agg_aggBuffer.setLong(0, agg_value13);
/* 247 */         } else {
/* 248 */           agg_aggBuffer.setNullAt(0);
/* 249 */         }
/* 250 */       }
/* 251 */       if (shouldStop()) return;
/* 252 */     }
/* 253 */
/* 254 */     agg_genMapIter = agg_aggregateHashMap.batch.rowIterator();
/* 255 */
/* 256 */     agg_mapIter = agg_plan.finishAggregate(agg_hashMap, agg_sorter);
/* 257 */   }
/* 258 */
/* 259 */   protected void processNext() throws java.io.IOException {
/* 260 */     /*** PRODUCE: TungstenAggregate(key=[k#51L], functions=[(sum(k#51L),mode=Final,isDistinct=false)], output=[k#51L,sum(k)#55L]) */
/* 261 */
/* 262 */     if (!agg_initAgg) {
/* 263 */       agg_initAgg = true;
/* 264 */       agg_doAggregateWithKeys();
/* 265 */     }
/* 266 */
/* 267 */     // output the result
/* 268 */     while (agg_genMapIter.hasNext()) {
/* 269 */       wholestagecodegen_metricValue.add(1);
/* 270 */       org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row row =
/* 271 */       (org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row)
/* 272 */       agg_genMapIter.next();
/* 273 */       append(row.copy());
/* 274 */
/* 275 */       if (shouldStop()) return;
/* 276 */     }
/* 277 */
/* 278 */     agg_aggregateHashMap.batch.close();
/* 279 */
/* 280 */     while (agg_mapIter.next()) {
/* 281 */       wholestagecodegen_metricValue.add(1);
/* 282 */       UnsafeRow agg_aggKey = (UnsafeRow) agg_mapIter.getKey();
/* 283 */       UnsafeRow agg_aggBuffer1 = (UnsafeRow) agg_mapIter.getValue();
/* 284 */
/* 285 */       /* input[0, bigint] */
/* 286 */       long agg_value21 = agg_aggKey.getLong(0);
/* 287 */       /* input[0, bigint] */
/* 288 */       boolean agg_isNull21 = agg_aggBuffer1.isNullAt(0);
/* 289 */       long agg_value22 = agg_isNull21 ? -1L : (agg_aggBuffer1.getLong(0));
/* 290 */
/* 291 */       /*** CONSUME: WholeStageCodegen */
/* 292 */
/* 293 */       agg_rowWriter1.zeroOutNullBytes();
/* 294 */
/* 295 */       agg_rowWriter1.write(0, agg_value21);
/* 296 */
/* 297 */       if (agg_isNull21) {
/* 298 */         agg_rowWriter1.setNullAt(1);
/* 299 */       } else {
/* 300 */         agg_rowWriter1.write(1, agg_value22);
/* 301 */       }
/* 302 */       append(agg_result1);
/* 303 */
/* 304 */       if (shouldStop()) return;
/* 305 */     }
/* 306 */
/* 307 */     agg_mapIter.close();
/* 308 */     if (agg_sorter == null) {
/* 309 */       agg_hashMap.free();
/* 310 */     }
/* 311 */   }
/* 312 */ }


/* 001 */ public Object generate(Object[] references) {
/* 002 */   return new GeneratedIterator(references);
/* 003 */ }
/* 004 */
/* 005 */ /** Codegened pipeline for:
/* 006 */ * TungstenAggregate(key=[k#51L], functions=[(sum(k#51L),mode=Partial,isDistinct=false)], output=[k#51L,sum#59L])
/* 007 */ +- Project [(id#4...
/* 008 */   */
/* 009 */ final class GeneratedIterator extends org.apache.spark.sql.execution.BufferedRowIterator {
/* 010 */   private Object[] references;
/* 011 */   private boolean agg_initAgg;
/* 012 */   private agg_GeneratedAggregateHashMap agg_aggregateHashMap;
/* 013 */   private java.util.Iterator<org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row> agg_genMapIter;
/* 014 */   private org.apache.spark.sql.execution.aggregate.TungstenAggregate agg_plan;
/* 015 */   private org.apache.spark.sql.execution.UnsafeFixedWidthAggregationMap agg_hashMap;
/* 016 */   private org.apache.spark.sql.execution.UnsafeKVExternalSorter agg_sorter;
/* 017 */   private org.apache.spark.unsafe.KVIterator agg_mapIter;
/* 018 */   private org.apache.spark.sql.execution.metric.LongSQLMetric range_numOutputRows;
/* 019 */   private org.apache.spark.sql.execution.metric.LongSQLMetricValue range_metricValue;
/* 020 */   private boolean range_initRange;
/* 021 */   private long range_partitionEnd;
/* 022 */   private long range_number;
/* 023 */   private boolean range_overflow;
/* 024 */   private scala.collection.Iterator range_input;
/* 025 */   private UnsafeRow range_result;
/* 026 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder range_holder;
/* 027 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter range_rowWriter;
/* 028 */   private UnsafeRow project_result;
/* 029 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder project_holder;
/* 030 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter project_rowWriter;
/* 031 */   private UnsafeRow agg_result;
/* 032 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder agg_holder;
/* 033 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter agg_rowWriter;
/* 034 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowJoiner agg_unsafeRowJoiner;
/* 035 */   private org.apache.spark.sql.execution.metric.LongSQLMetric wholestagecodegen_numOutputRows;
/* 036 */   private org.apache.spark.sql.execution.metric.LongSQLMetricValue wholestagecodegen_metricValue;
/* 037 */
/* 038 */   public GeneratedIterator(Object[] references) {
/* 039 */     this.references = references;
/* 040 */   }
/* 041 */
/* 042 */   public void init(int index, scala.collection.Iterator inputs[]) {
/* 043 */     partitionIndex = index;
/* 044 */     agg_initAgg = false;
/* 045 */     agg_aggregateHashMap = new agg_GeneratedAggregateHashMap();
/* 046 */
/* 047 */     this.agg_plan = (org.apache.spark.sql.execution.aggregate.TungstenAggregate) references[0];
/* 048 */
/* 049 */     this.range_numOutputRows = (org.apache.spark.sql.execution.metric.LongSQLMetric) references[1];
/* 050 */     range_metricValue = (org.apache.spark.sql.execution.metric.LongSQLMetricValue) range_numOutputRows.localValue();
/* 051 */     range_initRange = false;
/* 052 */     range_partitionEnd = 0L;
/* 053 */     range_number = 0L;
/* 054 */     range_overflow = false;
/* 055 */     range_input = inputs[0];
/* 056 */     range_result = new UnsafeRow(1);
/* 057 */     this.range_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(range_result, 0);
/* 058 */     this.range_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(range_holder, 1);
/* 059 */     project_result = new UnsafeRow(1);
/* 060 */     this.project_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(project_result, 0);
/* 061 */     this.project_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(project_holder, 1);
/* 062 */     agg_result = new UnsafeRow(1);
/* 063 */     this.agg_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(agg_result, 0);
/* 064 */     this.agg_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(agg_holder, 1);
/* 065 */     agg_unsafeRowJoiner = agg_plan.createUnsafeJoiner();
/* 066 */     this.wholestagecodegen_numOutputRows = (org.apache.spark.sql.execution.metric.LongSQLMetric) references[2];
/* 067 */     wholestagecodegen_metricValue = (org.apache.spark.sql.execution.metric.LongSQLMetricValue) wholestagecodegen_numOutputRows.localValue();
/* 068 */   }
/* 069 */
/* 070 */   public class agg_GeneratedAggregateHashMap {
/* 071 */     public org.apache.spark.sql.execution.vectorized.ColumnarBatch batch;
/* 072 */     private int[] buckets;
/* 073 */     private int numBuckets;
/* 074 */     private int maxSteps;
/* 075 */     private int numRows = 0;
/* 076 */     private org.apache.spark.sql.types.StructType schema =
/* 077 */     new org.apache.spark.sql.types.StructType()
/* 078 */     .add("k", org.apache.spark.sql.types.DataTypes.LongType)
/* 079 */     .add("sum", org.apache.spark.sql.types.DataTypes.LongType);
/* 080 */
/* 081 */     public agg_GeneratedAggregateHashMap() {
/* 082 */       int DEFAULT_CAPACITY = 1 << 16;
/* 083 */       double DEFAULT_LOAD_FACTOR = 0.25;
/* 084 */       int DEFAULT_MAX_STEPS = 5;
/* 085 */       assert (DEFAULT_CAPACITY > 0 && ((DEFAULT_CAPACITY & (DEFAULT_CAPACITY - 1)) == 0));
/* 086 */       this.maxSteps = DEFAULT_MAX_STEPS;
/* 087 */       numBuckets = (int) (DEFAULT_CAPACITY / DEFAULT_LOAD_FACTOR);
/* 088 */       batch = org.apache.spark.sql.execution.vectorized.ColumnarBatch.allocate(schema,
/* 089 */         org.apache.spark.memory.MemoryMode.ON_HEAP, DEFAULT_CAPACITY);
/* 090 */       buckets = new int[numBuckets];
/* 091 */       java.util.Arrays.fill(buckets, -1);
/* 092 */     }
/* 093 */
/* 094 */     public org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row findOrInsert(long agg_key) {
/* 095 */       long h = hash(agg_key);
/* 096 */       int step = 0;
/* 097 */       int idx = (int) h & (numBuckets - 1);
/* 098 */       while (step < maxSteps) {
/* 099 */         // Return bucket index if it's either an empty slot or already contains the key
/* 100 */         if (buckets[idx] == -1) {
/* 101 */           batch.column(0).putLong(numRows, agg_key);
/* 102 */           batch.column(1).putLong(numRows, 0);
/* 103 */           buckets[idx] = numRows++;
/* 104 */           return batch.getRow(buckets[idx]);
/* 105 */         } else if (equals(idx, agg_key)) {
/* 106 */           return batch.getRow(buckets[idx]);
/* 107 */         }
/* 108 */         idx = (idx + 1) & (numBuckets - 1);
/* 109 */         step++;
/* 110 */       }
/* 111 */       // Didn't find it
/* 112 */       return null;
/* 113 */     }
/* 114 */
/* 115 */     private boolean equals(int idx, long agg_key) {
/* 116 */       return batch.column(0).getLong(buckets[idx]) == agg_key;
/* 117 */     }
/* 118 */
/* 119 */     // TODO: Improve this hash function
/* 120 */     private long hash(long agg_key) {
/* 121 */       return agg_key;
/* 122 */     }
/* 123 */
/* 124 */   }
/* 125 */
/* 126 */   private void agg_doAggregateWithKeys() throws java.io.IOException {
/* 127 */     agg_hashMap = agg_plan.createHashMap();
/* 128 */
/* 129 */     /*** PRODUCE: Project [(id#48L & 3) AS k#51L] */
/* 130 */
/* 131 */     /*** PRODUCE: Range 0, 1, 1, 20971520, [id#48L] */
/* 132 */
/* 133 */     // initialize Range
/* 134 */     if (!range_initRange) {
/* 135 */       range_initRange = true;
/* 136 */       initRange(partitionIndex);
/* 137 */     }
/* 138 */
/* 139 */     while (!range_overflow && range_number < range_partitionEnd) {
/* 140 */       long range_value = range_number;
/* 141 */       range_number += 1L;
/* 142 */       if (range_number < range_value ^ 1L < 0) {
/* 143 */         range_overflow = true;
/* 144 */       }
/* 145 */
/* 146 */       /*** CONSUME: Project [(id#48L & 3) AS k#51L] */
/* 147 */
/* 148 */       /*** CONSUME: TungstenAggregate(key=[k#51L], functions=[(sum(k#51L),mode=Partial,isDistinct=false)], output=[k#51L,sum#59L]) */
/* 149 */       /* (input[0, bigint] & 3) */
/* 150 */       long project_value = -1L;
/* 151 */       project_value = range_value & 3L;
/* 152 */
/* 153 */       // generate grouping key
/* 154 */       agg_rowWriter.write(0, project_value);
/* 155 */       /* hash(input[0, bigint], 42) */
/* 156 */       int agg_value3 = 42;
/* 157 */
/* 158 */       agg_value3 = org.apache.spark.unsafe.hash.Murmur3_x86_32.hashLong(project_value, agg_value3);
/* 159 */       UnsafeRow agg_aggBuffer = null;
/* 160 */       org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row agg_aggregateRow = null;
/* 161 */       if (true) {
/* 162 */         agg_aggregateRow =
/* 163 */         agg_aggregateHashMap.findOrInsert(project_value);
/* 164 */         // try to get the buffer from hash map
/* 165 */         if (agg_aggregateRow == null) {
/* 166 */           agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 167 */         }
/* 168 */       }
/* 169 */       if (agg_aggregateRow == null && agg_aggBuffer == null) {
/* 170 */         if (agg_sorter == null) {
/* 171 */           agg_sorter = agg_hashMap.destructAndCreateExternalSorter();
/* 172 */         } else {
/* 173 */           agg_sorter.merge(agg_hashMap.destructAndCreateExternalSorter());
/* 174 */         }
/* 175 */
/* 176 */         // the hash map had be spilled, it should have enough memory now,
/* 177 */         // try  to allocate buffer again.
/* 178 */         agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 179 */         if (agg_aggBuffer == null) {
/* 180 */           // failed to allocate the first page
/* 181 */           throw new OutOfMemoryError("No enough memory for aggregation");
/* 182 */         }
/* 183 */       }
/* 184 */
/* 185 */       if (agg_aggregateRow != null) {
/* 186 */         // evaluate aggregate function
/* 187 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + cast(input[1, bigint] as bigint)) */
/* 188 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 189 */         /* input[0, bigint] */
/* 190 */         boolean agg_isNull6 = agg_aggregateRow.isNullAt(0);
/* 191 */         long agg_value7 = agg_isNull6 ? -1L : (agg_aggregateRow.getLong(0));
/* 192 */         boolean agg_isNull5 = agg_isNull6;
/* 193 */         long agg_value6 = agg_value7;
/* 194 */
/* 195 */         if (agg_isNull5) {
/* 196 */           /* cast(0 as bigint) */
/* 197 */           boolean agg_isNull7 = false;
/* 198 */           long agg_value8 = -1L;
/* 199 */           if (!false) {
/* 200 */             agg_value8 = (long) 0;
/* 201 */           }
/* 202 */           if (!agg_isNull7) {
/* 203 */             agg_isNull5 = false;
/* 204 */             agg_value6 = agg_value8;
/* 205 */           }
/* 206 */         }
/* 207 */         /* cast(input[1, bigint] as bigint) */
/* 208 */         boolean agg_isNull9 = false;
/* 209 */         long agg_value10 = -1L;
/* 210 */         if (!false) {
/* 211 */           agg_value10 = project_value;
/* 212 */         }
/* 213 */         long agg_value5 = -1L;
/* 214 */         agg_value5 = agg_value6 + agg_value10;
/* 215 */         // update aggregate row
/* 216 */         agg_aggregateRow.setLong(1, agg_value5);
/* 217 */       } else {
/* 218 */         // evaluate aggregate function
/* 219 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + cast(input[1, bigint] as bigint)) */
/* 220 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 221 */         /* input[0, bigint] */
/* 222 */         boolean agg_isNull13 = agg_aggBuffer.isNullAt(0);
/* 223 */         long agg_value14 = agg_isNull13 ? -1L : (agg_aggBuffer.getLong(0));
/* 224 */         boolean agg_isNull12 = agg_isNull13;
/* 225 */         long agg_value13 = agg_value14;
/* 226 */
/* 227 */         if (agg_isNull12) {
/* 228 */           /* cast(0 as bigint) */
/* 229 */           boolean agg_isNull14 = false;
/* 230 */           long agg_value15 = -1L;
/* 231 */           if (!false) {
/* 232 */             agg_value15 = (long) 0;
/* 233 */           }
/* 234 */           if (!agg_isNull14) {
/* 235 */             agg_isNull12 = false;
/* 236 */             agg_value13 = agg_value15;
/* 237 */           }
/* 238 */         }
/* 239 */         /* cast(input[1, bigint] as bigint) */
/* 240 */         boolean agg_isNull16 = false;
/* 241 */         long agg_value17 = -1L;
/* 242 */         if (!false) {
/* 243 */           agg_value17 = project_value;
/* 244 */         }
/* 245 */         long agg_value12 = -1L;
/* 246 */         agg_value12 = agg_value13 + agg_value17;
/* 247 */         // update aggregate buffer
/* 248 */         agg_aggBuffer.setLong(0, agg_value12);
/* 249 */       }
/* 250 */
/* 251 */       if (shouldStop()) return;
/* 252 */     }
/* 253 */
/* 254 */     agg_genMapIter = agg_aggregateHashMap.batch.rowIterator();
/* 255 */
/* 256 */     agg_mapIter = agg_plan.finishAggregate(agg_hashMap, agg_sorter);
/* 257 */   }
/* 258 */
/* 259 */   private void initRange(int idx) {
/* 260 */     java.math.BigInteger index = java.math.BigInteger.valueOf(idx);
/* 261 */     java.math.BigInteger numSlice = java.math.BigInteger.valueOf(1L);
/* 262 */     java.math.BigInteger numElement = java.math.BigInteger.valueOf(20971520L);
/* 263 */     java.math.BigInteger step = java.math.BigInteger.valueOf(1L);
/* 264 */     java.math.BigInteger start = java.math.BigInteger.valueOf(0L);
/* 265 */
/* 266 */     java.math.BigInteger st = index.multiply(numElement).divide(numSlice).multiply(step).add(start);
/* 267 */     if (st.compareTo(java.math.BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
/* 268 */       range_number = Long.MAX_VALUE;
/* 269 */     } else if (st.compareTo(java.math.BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
/* 270 */       range_number = Long.MIN_VALUE;
/* 271 */     } else {
/* 272 */       range_number = st.longValue();
/* 273 */     }
/* 274 */
/* 275 */     java.math.BigInteger end = index.add(java.math.BigInteger.ONE).multiply(numElement).divide(numSlice)
/* 276 */     .multiply(step).add(start);
/* 277 */     if (end.compareTo(java.math.BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
/* 278 */       range_partitionEnd = Long.MAX_VALUE;
/* 279 */     } else if (end.compareTo(java.math.BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
/* 280 */       range_partitionEnd = Long.MIN_VALUE;
/* 281 */     } else {
/* 282 */       range_partitionEnd = end.longValue();
/* 283 */     }
/* 284 */
/* 285 */     range_metricValue.add((range_partitionEnd - range_number) / 1L);
/* 286 */   }
/* 287 */
/* 288 */   protected void processNext() throws java.io.IOException {
/* 289 */     /*** PRODUCE: TungstenAggregate(key=[k#51L], functions=[(sum(k#51L),mode=Partial,isDistinct=false)], output=[k#51L,sum#59L]) */
/* 290 */
/* 291 */     if (!agg_initAgg) {
/* 292 */       agg_initAgg = true;
/* 293 */       agg_doAggregateWithKeys();
/* 294 */     }
/* 295 */
/* 296 */     // output the result
/* 297 */     while (agg_genMapIter.hasNext()) {
/* 298 */       wholestagecodegen_metricValue.add(1);
/* 299 */       org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row row =
/* 300 */       (org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row)
/* 301 */       agg_genMapIter.next();
/* 302 */       append(row.copy());
/* 303 */
/* 304 */       if (shouldStop()) return;
/* 305 */     }
/* 306 */
/* 307 */     agg_aggregateHashMap.batch.close();
/* 308 */
/* 309 */     while (agg_mapIter.next()) {
/* 310 */       wholestagecodegen_metricValue.add(1);
/* 311 */       UnsafeRow agg_aggKey = (UnsafeRow) agg_mapIter.getKey();
/* 312 */       UnsafeRow agg_aggBuffer1 = (UnsafeRow) agg_mapIter.getValue();
/* 313 */
/* 314 */       UnsafeRow agg_resultRow = agg_unsafeRowJoiner.join(agg_aggKey, agg_aggBuffer1);
/* 315 */
/* 316 */       /*** CONSUME: WholeStageCodegen */
/* 317 */
/* 318 */       append(agg_resultRow);
/* 319 */
/* 320 */       if (shouldStop()) return;
/* 321 */     }
/* 322 */
/* 323 */     agg_mapIter.close();
/* 324 */     if (agg_sorter == null) {
/* 325 */       agg_hashMap.free();
/* 326 */     }
/* 327 */   }
/* 328 */ }
