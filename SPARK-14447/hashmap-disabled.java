/* 001 */ public Object generate(Object[] references) {
/* 002 */   return new GeneratedIterator(references);
/* 003 */ }
/* 004 */
/* 005 */ /** Codegened pipeline for:
/* 006 */ * TungstenAggregate(key=[k#27L], functions=[(sum(k#27L),mode=Final,isDistinct=false)], output=[k#27L,sum(k)#31L])
/* 007 */ +- INPUT
/* 008 */ */
/* 009 */ final class GeneratedIterator extends org.apache.spark.sql.execution.BufferedRowIterator {
/* 010 */   private Object[] references;
/* 011 */   private boolean agg_initAgg;
/* 012 */   private org.apache.spark.sql.execution.aggregate.TungstenAggregate agg_plan;
/* 013 */   private org.apache.spark.sql.execution.UnsafeFixedWidthAggregationMap agg_hashMap;
/* 014 */   private org.apache.spark.sql.execution.UnsafeKVExternalSorter agg_sorter;
/* 015 */   private org.apache.spark.unsafe.KVIterator agg_mapIter;
/* 016 */   private scala.collection.Iterator inputadapter_input;
/* 017 */   private UnsafeRow agg_result;
/* 018 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder agg_holder;
/* 019 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter agg_rowWriter;
/* 020 */   private UnsafeRow agg_result1;
/* 021 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder agg_holder1;
/* 022 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter agg_rowWriter1;
/* 023 */   private org.apache.spark.sql.execution.metric.LongSQLMetric wholestagecodegen_numOutputRows;
/* 024 */   private org.apache.spark.sql.execution.metric.LongSQLMetricValue wholestagecodegen_metricValue;
/* 025 */
/* 026 */   public GeneratedIterator(Object[] references) {
/* 027 */     this.references = references;
/* 028 */   }
/* 029 */
/* 030 */   public void init(int index, scala.collection.Iterator inputs[]) {
/* 031 */     partitionIndex = index;
/* 032 */     agg_initAgg = false;
/* 033 */     this.agg_plan = (org.apache.spark.sql.execution.aggregate.TungstenAggregate) references[0];
/* 034 */
/* 035 */     inputadapter_input = inputs[0];
/* 036 */     agg_result = new UnsafeRow(1);
/* 037 */     this.agg_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(agg_result, 0);
/* 038 */     this.agg_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(agg_holder, 1);
/* 039 */     agg_result1 = new UnsafeRow(2);
/* 040 */     this.agg_holder1 = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(agg_result1, 0);
/* 041 */     this.agg_rowWriter1 = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(agg_holder1, 2);
/* 042 */     this.wholestagecodegen_numOutputRows = (org.apache.spark.sql.execution.metric.LongSQLMetric) references[1];
/* 043 */     wholestagecodegen_metricValue = (org.apache.spark.sql.execution.metric.LongSQLMetricValue) wholestagecodegen_numOutputRows.localValue();
/* 044 */   }
/* 045 */
/* 046 */   private void agg_doAggregateWithKeys() throws java.io.IOException {
/* 047 */     agg_hashMap = agg_plan.createHashMap();
/* 048 */
/* 049 */     /*** PRODUCE: INPUT */
/* 050 */
/* 051 */     while (inputadapter_input.hasNext()) {
/* 052 */       InternalRow inputadapter_row = (InternalRow) inputadapter_input.next();
/* 053 */       /*** CONSUME: TungstenAggregate(key=[k#27L], functions=[(sum(k#27L),mode=Final,isDistinct=false)], output=[k#27L,sum(k)#31L]) */
/* 054 */       /* input[0, bigint] */
/* 055 */       long inputadapter_value = inputadapter_row.getLong(0);
/* 056 */       /* input[1, bigint] */
/* 057 */       boolean inputadapter_isNull1 = inputadapter_row.isNullAt(1);
/* 058 */       long inputadapter_value1 = inputadapter_isNull1 ? -1L : (inputadapter_row.getLong(1));
/* 059 */
/* 060 */       // generate grouping key
/* 061 */       agg_rowWriter.write(0, inputadapter_value);
/* 062 */       /* hash(input[0, bigint], 42) */
/* 063 */       int agg_value3 = 42;
/* 064 */
/* 065 */       agg_value3 = org.apache.spark.unsafe.hash.Murmur3_x86_32.hashLong(inputadapter_value, agg_value3);
/* 066 */       UnsafeRow agg_aggBuffer = null;
/* 067 */       org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row agg_aggregateRow = null;
/* 068 */       if (true) {
/* 069 */         // try to get the buffer from hash map
/* 070 */         if (agg_aggregateRow == null) {
/* 071 */           agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 072 */         }
/* 073 */       }
/* 074 */       if (agg_aggregateRow == null && agg_aggBuffer == null) {
/* 075 */         if (agg_sorter == null) {
/* 076 */           agg_sorter = agg_hashMap.destructAndCreateExternalSorter();
/* 077 */         } else {
/* 078 */           agg_sorter.merge(agg_hashMap.destructAndCreateExternalSorter());
/* 079 */         }
/* 080 */
/* 081 */         // the hash map had be spilled, it should have enough memory now,
/* 082 */         // try  to allocate buffer again.
/* 083 */         agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 084 */         if (agg_aggBuffer == null) {
/* 085 */           // failed to allocate the first page
/* 086 */           throw new OutOfMemoryError("No enough memory for aggregation");
/* 087 */         }
/* 088 */       }
/* 089 */
/* 090 */       if (agg_aggregateRow != null) {
/* 091 */         // evaluate aggregate function
/* 092 */         /* coalesce((coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]), input[0, bigint]) */
/* 093 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]) */
/* 094 */         boolean agg_isNull5 = true;
/* 095 */         long agg_value6 = -1L;
/* 096 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 097 */         /* input[0, bigint] */
/* 098 */         boolean agg_isNull7 = agg_aggregateRow.isNullAt(0);
/* 099 */         long agg_value8 = agg_isNull7 ? -1L : (agg_aggregateRow.getLong(0));
/* 100 */         boolean agg_isNull6 = agg_isNull7;
/* 101 */         long agg_value7 = agg_value8;
/* 102 */
/* 103 */         if (agg_isNull6) {
/* 104 */           /* cast(0 as bigint) */
/* 105 */           boolean agg_isNull8 = false;
/* 106 */           long agg_value9 = -1L;
/* 107 */           if (!false) {
/* 108 */             agg_value9 = (long) 0;
/* 109 */           }
/* 110 */           if (!agg_isNull8) {
/* 111 */             agg_isNull6 = false;
/* 112 */             agg_value7 = agg_value9;
/* 113 */           }
/* 114 */         }
/* 115 */
/* 116 */         if (!inputadapter_isNull1) {
/* 117 */           agg_isNull5 = false; // resultCode could change nullability.
/* 118 */           agg_value6 = agg_value7 + inputadapter_value1;
/* 119 */
/* 120 */         }
/* 121 */         boolean agg_isNull4 = agg_isNull5;
/* 122 */         long agg_value5 = agg_value6;
/* 123 */
/* 124 */         if (agg_isNull4) {
/* 125 */           /* input[0, bigint] */
/* 126 */           boolean agg_isNull11 = agg_aggregateRow.isNullAt(0);
/* 127 */           long agg_value12 = agg_isNull11 ? -1L : (agg_aggregateRow.getLong(0));
/* 128 */           if (!agg_isNull11) {
/* 129 */             agg_isNull4 = false;
/* 130 */             agg_value5 = agg_value12;
/* 131 */           }
/* 132 */         }
/* 133 */         // update aggregate row
/* 134 */         if (!agg_isNull4) {
/* 135 */           agg_aggregateRow.setLong(1, agg_value5);
/* 136 */         } else {
/* 137 */           agg_aggregateRow.setNullAt(1);
/* 138 */         }
/* 139 */       } else {
/* 140 */         // evaluate aggregate function
/* 141 */         /* coalesce((coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]), input[0, bigint]) */
/* 142 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + input[2, bigint]) */
/* 143 */         boolean agg_isNull13 = true;
/* 144 */         long agg_value14 = -1L;
/* 145 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 146 */         /* input[0, bigint] */
/* 147 */         boolean agg_isNull15 = agg_aggBuffer.isNullAt(0);
/* 148 */         long agg_value16 = agg_isNull15 ? -1L : (agg_aggBuffer.getLong(0));
/* 149 */         boolean agg_isNull14 = agg_isNull15;
/* 150 */         long agg_value15 = agg_value16;
/* 151 */
/* 152 */         if (agg_isNull14) {
/* 153 */           /* cast(0 as bigint) */
/* 154 */           boolean agg_isNull16 = false;
/* 155 */           long agg_value17 = -1L;
/* 156 */           if (!false) {
/* 157 */             agg_value17 = (long) 0;
/* 158 */           }
/* 159 */           if (!agg_isNull16) {
/* 160 */             agg_isNull14 = false;
/* 161 */             agg_value15 = agg_value17;
/* 162 */           }
/* 163 */         }
/* 164 */
/* 165 */         if (!inputadapter_isNull1) {
/* 166 */           agg_isNull13 = false; // resultCode could change nullability.
/* 167 */           agg_value14 = agg_value15 + inputadapter_value1;
/* 168 */
/* 169 */         }
/* 170 */         boolean agg_isNull12 = agg_isNull13;
/* 171 */         long agg_value13 = agg_value14;
/* 172 */
/* 173 */         if (agg_isNull12) {
/* 174 */           /* input[0, bigint] */
/* 175 */           boolean agg_isNull19 = agg_aggBuffer.isNullAt(0);
/* 176 */           long agg_value20 = agg_isNull19 ? -1L : (agg_aggBuffer.getLong(0));
/* 177 */           if (!agg_isNull19) {
/* 178 */             agg_isNull12 = false;
/* 179 */             agg_value13 = agg_value20;
/* 180 */           }
/* 181 */         }
/* 182 */         // update aggregate buffer
/* 183 */         if (!agg_isNull12) {
/* 184 */           agg_aggBuffer.setLong(0, agg_value13);
/* 185 */         } else {
/* 186 */           agg_aggBuffer.setNullAt(0);
/* 187 */         }
/* 188 */       }
/* 189 */       if (shouldStop()) return;
/* 190 */     }
/* 191 */
/* 192 */     agg_mapIter = agg_plan.finishAggregate(agg_hashMap, agg_sorter);
/* 193 */   }
/* 194 */
/* 195 */   protected void processNext() throws java.io.IOException {
/* 196 */     /*** PRODUCE: TungstenAggregate(key=[k#27L], functions=[(sum(k#27L),mode=Final,isDistinct=false)], output=[k#27L,sum(k)#31L]) */
/* 197 */
/* 198 */     if (!agg_initAgg) {
/* 199 */       agg_initAgg = true;
/* 200 */       agg_doAggregateWithKeys();
/* 201 */     }
/* 202 */
/* 203 */     // output the result
/* 204 */
/* 205 */     while (agg_mapIter.next()) {
/* 206 */       wholestagecodegen_metricValue.add(1);
/* 207 */       UnsafeRow agg_aggKey = (UnsafeRow) agg_mapIter.getKey();
/* 208 */       UnsafeRow agg_aggBuffer1 = (UnsafeRow) agg_mapIter.getValue();
/* 209 */
/* 210 */       /* input[0, bigint] */
/* 211 */       long agg_value21 = agg_aggKey.getLong(0);
/* 212 */       /* input[0, bigint] */
/* 213 */       boolean agg_isNull21 = agg_aggBuffer1.isNullAt(0);
/* 214 */       long agg_value22 = agg_isNull21 ? -1L : (agg_aggBuffer1.getLong(0));
/* 215 */
/* 216 */       /*** CONSUME: WholeStageCodegen */
/* 217 */
/* 218 */       agg_rowWriter1.zeroOutNullBytes();
/* 219 */
/* 220 */       agg_rowWriter1.write(0, agg_value21);
/* 221 */
/* 222 */       if (agg_isNull21) {
/* 223 */         agg_rowWriter1.setNullAt(1);
/* 224 */       } else {
/* 225 */         agg_rowWriter1.write(1, agg_value22);
/* 226 */       }
/* 227 */       append(agg_result1);
/* 228 */
/* 229 */       if (shouldStop()) return;
/* 230 */     }
/* 231 */
/* 232 */     agg_mapIter.close();
/* 233 */     if (agg_sorter == null) {
/* 234 */       agg_hashMap.free();
/* 235 */     }
/* 236 */   }
/* 237 */ }


/* 001 */ public Object generate(Object[] references) {
/* 002 */   return new GeneratedIterator(references);
/* 003 */ }
/* 004 */
/* 005 */ /** Codegened pipeline for:
/* 006 */ * TungstenAggregate(key=[k#27L], functions=[(sum(k#27L),mode=Partial,isDistinct=false)], output=[k#27L,sum#35L])
/* 007 */ +- Project [(id#2...
/* 008 */   */
/* 009 */ final class GeneratedIterator extends org.apache.spark.sql.execution.BufferedRowIterator {
/* 010 */   private Object[] references;
/* 011 */   private boolean agg_initAgg;
/* 012 */   private org.apache.spark.sql.execution.aggregate.TungstenAggregate agg_plan;
/* 013 */   private org.apache.spark.sql.execution.UnsafeFixedWidthAggregationMap agg_hashMap;
/* 014 */   private org.apache.spark.sql.execution.UnsafeKVExternalSorter agg_sorter;
/* 015 */   private org.apache.spark.unsafe.KVIterator agg_mapIter;
/* 016 */   private org.apache.spark.sql.execution.metric.LongSQLMetric range_numOutputRows;
/* 017 */   private org.apache.spark.sql.execution.metric.LongSQLMetricValue range_metricValue;
/* 018 */   private boolean range_initRange;
/* 019 */   private long range_partitionEnd;
/* 020 */   private long range_number;
/* 021 */   private boolean range_overflow;
/* 022 */   private scala.collection.Iterator range_input;
/* 023 */   private UnsafeRow range_result;
/* 024 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder range_holder;
/* 025 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter range_rowWriter;
/* 026 */   private UnsafeRow project_result;
/* 027 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder project_holder;
/* 028 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter project_rowWriter;
/* 029 */   private UnsafeRow agg_result;
/* 030 */   private org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder agg_holder;
/* 031 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter agg_rowWriter;
/* 032 */   private org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowJoiner agg_unsafeRowJoiner;
/* 033 */   private org.apache.spark.sql.execution.metric.LongSQLMetric wholestagecodegen_numOutputRows;
/* 034 */   private org.apache.spark.sql.execution.metric.LongSQLMetricValue wholestagecodegen_metricValue;
/* 035 */
/* 036 */   public GeneratedIterator(Object[] references) {
/* 037 */     this.references = references;
/* 038 */   }
/* 039 */
/* 040 */   public void init(int index, scala.collection.Iterator inputs[]) {
/* 041 */     partitionIndex = index;
/* 042 */     agg_initAgg = false;
/* 043 */     this.agg_plan = (org.apache.spark.sql.execution.aggregate.TungstenAggregate) references[0];
/* 044 */
/* 045 */     this.range_numOutputRows = (org.apache.spark.sql.execution.metric.LongSQLMetric) references[1];
/* 046 */     range_metricValue = (org.apache.spark.sql.execution.metric.LongSQLMetricValue) range_numOutputRows.localValue();
/* 047 */     range_initRange = false;
/* 048 */     range_partitionEnd = 0L;
/* 049 */     range_number = 0L;
/* 050 */     range_overflow = false;
/* 051 */     range_input = inputs[0];
/* 052 */     range_result = new UnsafeRow(1);
/* 053 */     this.range_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(range_result, 0);
/* 054 */     this.range_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(range_holder, 1);
/* 055 */     project_result = new UnsafeRow(1);
/* 056 */     this.project_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(project_result, 0);
/* 057 */     this.project_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(project_holder, 1);
/* 058 */     agg_result = new UnsafeRow(1);
/* 059 */     this.agg_holder = new org.apache.spark.sql.catalyst.expressions.codegen.BufferHolder(agg_result, 0);
/* 060 */     this.agg_rowWriter = new org.apache.spark.sql.catalyst.expressions.codegen.UnsafeRowWriter(agg_holder, 1);
/* 061 */     agg_unsafeRowJoiner = agg_plan.createUnsafeJoiner();
/* 062 */     this.wholestagecodegen_numOutputRows = (org.apache.spark.sql.execution.metric.LongSQLMetric) references[2];
/* 063 */     wholestagecodegen_metricValue = (org.apache.spark.sql.execution.metric.LongSQLMetricValue) wholestagecodegen_numOutputRows.localValue();
/* 064 */   }
/* 065 */
/* 066 */   private void agg_doAggregateWithKeys() throws java.io.IOException {
/* 067 */     agg_hashMap = agg_plan.createHashMap();
/* 068 */
/* 069 */     /*** PRODUCE: Project [(id#24L & 3) AS k#27L] */
/* 070 */
/* 071 */     /*** PRODUCE: Range 0, 1, 1, 20971520, [id#24L] */
/* 072 */
/* 073 */     // initialize Range
/* 074 */     if (!range_initRange) {
/* 075 */       range_initRange = true;
/* 076 */       initRange(partitionIndex);
/* 077 */     }
/* 078 */
/* 079 */     while (!range_overflow && range_number < range_partitionEnd) {
/* 080 */       long range_value = range_number;
/* 081 */       range_number += 1L;
/* 082 */       if (range_number < range_value ^ 1L < 0) {
/* 083 */         range_overflow = true;
/* 084 */       }
/* 085 */
/* 086 */       /*** CONSUME: Project [(id#24L & 3) AS k#27L] */
/* 087 */
/* 088 */       /*** CONSUME: TungstenAggregate(key=[k#27L], functions=[(sum(k#27L),mode=Partial,isDistinct=false)], output=[k#27L,sum#35L]) */
/* 089 */       /* (input[0, bigint] & 3) */
/* 090 */       long project_value = -1L;
/* 091 */       project_value = range_value & 3L;
/* 092 */
/* 093 */       // generate grouping key
/* 094 */       agg_rowWriter.write(0, project_value);
/* 095 */       /* hash(input[0, bigint], 42) */
/* 096 */       int agg_value3 = 42;
/* 097 */
/* 098 */       agg_value3 = org.apache.spark.unsafe.hash.Murmur3_x86_32.hashLong(project_value, agg_value3);
/* 099 */       UnsafeRow agg_aggBuffer = null;
/* 100 */       org.apache.spark.sql.execution.vectorized.ColumnarBatch.Row agg_aggregateRow = null;
/* 101 */       if (true) {
/* 102 */         // try to get the buffer from hash map
/* 103 */         if (agg_aggregateRow == null) {
/* 104 */           agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 105 */         }
/* 106 */       }
/* 107 */       if (agg_aggregateRow == null && agg_aggBuffer == null) {
/* 108 */         if (agg_sorter == null) {
/* 109 */           agg_sorter = agg_hashMap.destructAndCreateExternalSorter();
/* 110 */         } else {
/* 111 */           agg_sorter.merge(agg_hashMap.destructAndCreateExternalSorter());
/* 112 */         }
/* 113 */
/* 114 */         // the hash map had be spilled, it should have enough memory now,
/* 115 */         // try  to allocate buffer again.
/* 116 */         agg_aggBuffer = agg_hashMap.getAggregationBufferFromUnsafeRow(agg_result, agg_value3);
/* 117 */         if (agg_aggBuffer == null) {
/* 118 */           // failed to allocate the first page
/* 119 */           throw new OutOfMemoryError("No enough memory for aggregation");
/* 120 */         }
/* 121 */       }
/* 122 */
/* 123 */       if (agg_aggregateRow != null) {
/* 124 */         // evaluate aggregate function
/* 125 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + cast(input[1, bigint] as bigint)) */
/* 126 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 127 */         /* input[0, bigint] */
/* 128 */         boolean agg_isNull6 = agg_aggregateRow.isNullAt(0);
/* 129 */         long agg_value7 = agg_isNull6 ? -1L : (agg_aggregateRow.getLong(0));
/* 130 */         boolean agg_isNull5 = agg_isNull6;
/* 131 */         long agg_value6 = agg_value7;
/* 132 */
/* 133 */         if (agg_isNull5) {
/* 134 */           /* cast(0 as bigint) */
/* 135 */           boolean agg_isNull7 = false;
/* 136 */           long agg_value8 = -1L;
/* 137 */           if (!false) {
/* 138 */             agg_value8 = (long) 0;
/* 139 */           }
/* 140 */           if (!agg_isNull7) {
/* 141 */             agg_isNull5 = false;
/* 142 */             agg_value6 = agg_value8;
/* 143 */           }
/* 144 */         }
/* 145 */         /* cast(input[1, bigint] as bigint) */
/* 146 */         boolean agg_isNull9 = false;
/* 147 */         long agg_value10 = -1L;
/* 148 */         if (!false) {
/* 149 */           agg_value10 = project_value;
/* 150 */         }
/* 151 */         long agg_value5 = -1L;
/* 152 */         agg_value5 = agg_value6 + agg_value10;
/* 153 */         // update aggregate row
/* 154 */         agg_aggregateRow.setLong(1, agg_value5);
/* 155 */       } else {
/* 156 */         // evaluate aggregate function
/* 157 */         /* (coalesce(input[0, bigint], cast(0 as bigint)) + cast(input[1, bigint] as bigint)) */
/* 158 */         /* coalesce(input[0, bigint], cast(0 as bigint)) */
/* 159 */         /* input[0, bigint] */
/* 160 */         boolean agg_isNull13 = agg_aggBuffer.isNullAt(0);
/* 161 */         long agg_value14 = agg_isNull13 ? -1L : (agg_aggBuffer.getLong(0));
/* 162 */         boolean agg_isNull12 = agg_isNull13;
/* 163 */         long agg_value13 = agg_value14;
/* 164 */
/* 165 */         if (agg_isNull12) {
/* 166 */           /* cast(0 as bigint) */
/* 167 */           boolean agg_isNull14 = false;
/* 168 */           long agg_value15 = -1L;
/* 169 */           if (!false) {
/* 170 */             agg_value15 = (long) 0;
/* 171 */           }
/* 172 */           if (!agg_isNull14) {
/* 173 */             agg_isNull12 = false;
/* 174 */             agg_value13 = agg_value15;
/* 175 */           }
/* 176 */         }
/* 177 */         /* cast(input[1, bigint] as bigint) */
/* 178 */         boolean agg_isNull16 = false;
/* 179 */         long agg_value17 = -1L;
/* 180 */         if (!false) {
/* 181 */           agg_value17 = project_value;
/* 182 */         }
/* 183 */         long agg_value12 = -1L;
/* 184 */         agg_value12 = agg_value13 + agg_value17;
/* 185 */         // update aggregate buffer
/* 186 */         agg_aggBuffer.setLong(0, agg_value12);
/* 187 */       }
/* 188 */
/* 189 */       if (shouldStop()) return;
/* 190 */     }
/* 191 */
/* 192 */     agg_mapIter = agg_plan.finishAggregate(agg_hashMap, agg_sorter);
/* 193 */   }
/* 194 */
/* 195 */   private void initRange(int idx) {
/* 196 */     java.math.BigInteger index = java.math.BigInteger.valueOf(idx);
/* 197 */     java.math.BigInteger numSlice = java.math.BigInteger.valueOf(1L);
/* 198 */     java.math.BigInteger numElement = java.math.BigInteger.valueOf(20971520L);
/* 199 */     java.math.BigInteger step = java.math.BigInteger.valueOf(1L);
/* 200 */     java.math.BigInteger start = java.math.BigInteger.valueOf(0L);
/* 201 */
/* 202 */     java.math.BigInteger st = index.multiply(numElement).divide(numSlice).multiply(step).add(start);
/* 203 */     if (st.compareTo(java.math.BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
/* 204 */       range_number = Long.MAX_VALUE;
/* 205 */     } else if (st.compareTo(java.math.BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
/* 206 */       range_number = Long.MIN_VALUE;
/* 207 */     } else {
/* 208 */       range_number = st.longValue();
/* 209 */     }
/* 210 */
/* 211 */     java.math.BigInteger end = index.add(java.math.BigInteger.ONE).multiply(numElement).divide(numSlice)
/* 212 */     .multiply(step).add(start);
/* 213 */     if (end.compareTo(java.math.BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
/* 214 */       range_partitionEnd = Long.MAX_VALUE;
/* 215 */     } else if (end.compareTo(java.math.BigInteger.valueOf(Long.MIN_VALUE)) < 0) {
/* 216 */       range_partitionEnd = Long.MIN_VALUE;
/* 217 */     } else {
/* 218 */       range_partitionEnd = end.longValue();
/* 219 */     }
/* 220 */
/* 221 */     range_metricValue.add((range_partitionEnd - range_number) / 1L);
/* 222 */   }
/* 223 */
/* 224 */   protected void processNext() throws java.io.IOException {
/* 225 */     /*** PRODUCE: TungstenAggregate(key=[k#27L], functions=[(sum(k#27L),mode=Partial,isDistinct=false)], output=[k#27L,sum#35L]) */
/* 226 */     
/* 227 */     if (!agg_initAgg) {
/* 228 */       agg_initAgg = true;
/* 229 */       agg_doAggregateWithKeys();
/* 230 */     }
/* 231 */
/* 232 */     // output the result
/* 233 */
/* 234 */     while (agg_mapIter.next()) {
/* 235 */       wholestagecodegen_metricValue.add(1);
/* 236 */       UnsafeRow agg_aggKey = (UnsafeRow) agg_mapIter.getKey();
/* 237 */       UnsafeRow agg_aggBuffer1 = (UnsafeRow) agg_mapIter.getValue();
/* 238 */
/* 239 */       UnsafeRow agg_resultRow = agg_unsafeRowJoiner.join(agg_aggKey, agg_aggBuffer1);
/* 240 */
/* 241 */       /*** CONSUME: WholeStageCodegen */
/* 242 */
/* 243 */       append(agg_resultRow);
/* 244 */
/* 245 */       if (shouldStop()) return;
/* 246 */     }
/* 247 */
/* 248 */     agg_mapIter.close();
/* 249 */     if (agg_sorter == null) {
/* 250 */       agg_hashMap.free();
/* 251 */     }
/* 252 */   }
/* 253 */ }
