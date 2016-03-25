/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.apache.sysml.runtime.matrix.operators;

import org.apache.sysml.runtime.functionobjects.Builtin;
import org.apache.sysml.runtime.functionobjects.ValueFunction;

public class UnaryOperator extends Operator 
{
	private static final long serialVersionUID = 2441990876648978637L;

	public ValueFunction fn;
	private int k; //num threads

	public UnaryOperator(ValueFunction p) {
		this(p, 1); //default single-threaded
	}
	
	public UnaryOperator(ValueFunction p, int numThreads)
	{
		fn = p;
		sparseSafe = false;
		k = numThreads;
		
		if(fn instanceof Builtin)
		{
			Builtin f=(Builtin)fn;
			if(f.bFunc==Builtin.BuiltinFunctionCode.SIN || f.bFunc==Builtin.BuiltinFunctionCode.TAN 
					|| f.bFunc==Builtin.BuiltinFunctionCode.ROUND || f.bFunc==Builtin.BuiltinFunctionCode.ABS
					|| f.bFunc==Builtin.BuiltinFunctionCode.SQRT || f.bFunc==Builtin.BuiltinFunctionCode.SPROP
					|| f.bFunc==Builtin.BuiltinFunctionCode.SELP || f.bFunc==Builtin.BuiltinFunctionCode.LOG_NZ
					|| f.bFunc==Builtin.BuiltinFunctionCode.SIGN )
			{
				sparseSafe = true;
			}
		}
	}
	
	public int getNumThreads() {
		return k;
	}
}
