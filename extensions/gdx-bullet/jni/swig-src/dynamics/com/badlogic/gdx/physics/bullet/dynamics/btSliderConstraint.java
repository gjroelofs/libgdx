/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.badlogic.gdx.physics.bullet.dynamics;

import com.badlogic.gdx.physics.bullet.BulletBase;
import com.badlogic.gdx.physics.bullet.linearmath.*;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class btSliderConstraint extends btTypedConstraint {
	private long swigCPtr;
	
	protected btSliderConstraint(final String className, long cPtr, boolean cMemoryOwn) {
		super(className, DynamicsJNI.btSliderConstraint_SWIGUpcast(cPtr), cMemoryOwn);
		swigCPtr = cPtr;
	}
	
	/** Construct a new btSliderConstraint, normally you should not need this constructor it's intended for low-level usage. */
	public btSliderConstraint(long cPtr, boolean cMemoryOwn) {
		this("btSliderConstraint", cPtr, cMemoryOwn);
		construct();
	}
	
	@Override
	protected void reset(long cPtr, boolean cMemoryOwn) {
		if (!destroyed)
			destroy();
		super.reset(DynamicsJNI.btSliderConstraint_SWIGUpcast(swigCPtr = cPtr), cMemoryOwn);
	}
	
	public static long getCPtr(btSliderConstraint obj) {
		return (obj == null) ? 0 : obj.swigCPtr;
	}

	@Override
	protected void finalize() throws Throwable {
		if (!destroyed)
			destroy();
		super.finalize();
	}

  @Override protected synchronized void delete() {
		if (swigCPtr != 0) {
			if (swigCMemOwn) {
				swigCMemOwn = false;
				DynamicsJNI.delete_btSliderConstraint(swigCPtr);
			}
			swigCPtr = 0;
		}
		super.delete();
	}

  public btSliderConstraint(btRigidBody rbA, btRigidBody rbB, Matrix4 frameInA, Matrix4 frameInB, boolean useLinearReferenceFrameA) {
    this(DynamicsJNI.new_btSliderConstraint__SWIG_0(btRigidBody.getCPtr(rbA), rbA, btRigidBody.getCPtr(rbB), rbB, frameInA, frameInB, useLinearReferenceFrameA), true);
  }

  public btSliderConstraint(btRigidBody rbB, Matrix4 frameInB, boolean useLinearReferenceFrameA) {
    this(DynamicsJNI.new_btSliderConstraint__SWIG_1(btRigidBody.getCPtr(rbB), rbB, frameInB, useLinearReferenceFrameA), true);
  }

  public void getInfo1NonVirtual(SWIGTYPE_p_btTypedConstraint__btConstraintInfo1 info) {
    DynamicsJNI.btSliderConstraint_getInfo1NonVirtual(swigCPtr, this, SWIGTYPE_p_btTypedConstraint__btConstraintInfo1.getCPtr(info));
  }

  public void getInfo2NonVirtual(btConstraintInfo2 info, Matrix4 transA, Matrix4 transB, Vector3 linVelA, Vector3 linVelB, float rbAinvMass, float rbBinvMass) {
    DynamicsJNI.btSliderConstraint_getInfo2NonVirtual(swigCPtr, this, btConstraintInfo2.getCPtr(info), info, transA, transB, linVelA, linVelB, rbAinvMass, rbBinvMass);
  }

  public btRigidBody getRigidBodyA() {
    return new btRigidBody(DynamicsJNI.btSliderConstraint_getRigidBodyA(swigCPtr, this), false);
  }

  public btRigidBody getRigidBodyB() {
    return new btRigidBody(DynamicsJNI.btSliderConstraint_getRigidBodyB(swigCPtr, this), false);
  }

  public Matrix4 getCalculatedTransformA() {
	return DynamicsJNI.btSliderConstraint_getCalculatedTransformA(swigCPtr, this);
}

  public Matrix4 getCalculatedTransformB() {
	return DynamicsJNI.btSliderConstraint_getCalculatedTransformB(swigCPtr, this);
}

  public Matrix4 getFrameOffsetA() {
	return DynamicsJNI.btSliderConstraint_getFrameOffsetA__SWIG_0(swigCPtr, this);
}

  public Matrix4 getFrameOffsetB() {
	return DynamicsJNI.btSliderConstraint_getFrameOffsetB__SWIG_0(swigCPtr, this);
}

  public float getLowerLinLimit() {
    return DynamicsJNI.btSliderConstraint_getLowerLinLimit(swigCPtr, this);
  }

  public void setLowerLinLimit(float lowerLimit) {
    DynamicsJNI.btSliderConstraint_setLowerLinLimit(swigCPtr, this, lowerLimit);
  }

  public float getUpperLinLimit() {
    return DynamicsJNI.btSliderConstraint_getUpperLinLimit(swigCPtr, this);
  }

  public void setUpperLinLimit(float upperLimit) {
    DynamicsJNI.btSliderConstraint_setUpperLinLimit(swigCPtr, this, upperLimit);
  }

  public float getLowerAngLimit() {
    return DynamicsJNI.btSliderConstraint_getLowerAngLimit(swigCPtr, this);
  }

  public void setLowerAngLimit(float lowerLimit) {
    DynamicsJNI.btSliderConstraint_setLowerAngLimit(swigCPtr, this, lowerLimit);
  }

  public float getUpperAngLimit() {
    return DynamicsJNI.btSliderConstraint_getUpperAngLimit(swigCPtr, this);
  }

  public void setUpperAngLimit(float upperLimit) {
    DynamicsJNI.btSliderConstraint_setUpperAngLimit(swigCPtr, this, upperLimit);
  }

  public boolean getUseLinearReferenceFrameA() {
    return DynamicsJNI.btSliderConstraint_getUseLinearReferenceFrameA(swigCPtr, this);
  }

  public float getSoftnessDirLin() {
    return DynamicsJNI.btSliderConstraint_getSoftnessDirLin(swigCPtr, this);
  }

  public float getRestitutionDirLin() {
    return DynamicsJNI.btSliderConstraint_getRestitutionDirLin(swigCPtr, this);
  }

  public float getDampingDirLin() {
    return DynamicsJNI.btSliderConstraint_getDampingDirLin(swigCPtr, this);
  }

  public float getSoftnessDirAng() {
    return DynamicsJNI.btSliderConstraint_getSoftnessDirAng(swigCPtr, this);
  }

  public float getRestitutionDirAng() {
    return DynamicsJNI.btSliderConstraint_getRestitutionDirAng(swigCPtr, this);
  }

  public float getDampingDirAng() {
    return DynamicsJNI.btSliderConstraint_getDampingDirAng(swigCPtr, this);
  }

  public float getSoftnessLimLin() {
    return DynamicsJNI.btSliderConstraint_getSoftnessLimLin(swigCPtr, this);
  }

  public float getRestitutionLimLin() {
    return DynamicsJNI.btSliderConstraint_getRestitutionLimLin(swigCPtr, this);
  }

  public float getDampingLimLin() {
    return DynamicsJNI.btSliderConstraint_getDampingLimLin(swigCPtr, this);
  }

  public float getSoftnessLimAng() {
    return DynamicsJNI.btSliderConstraint_getSoftnessLimAng(swigCPtr, this);
  }

  public float getRestitutionLimAng() {
    return DynamicsJNI.btSliderConstraint_getRestitutionLimAng(swigCPtr, this);
  }

  public float getDampingLimAng() {
    return DynamicsJNI.btSliderConstraint_getDampingLimAng(swigCPtr, this);
  }

  public float getSoftnessOrthoLin() {
    return DynamicsJNI.btSliderConstraint_getSoftnessOrthoLin(swigCPtr, this);
  }

  public float getRestitutionOrthoLin() {
    return DynamicsJNI.btSliderConstraint_getRestitutionOrthoLin(swigCPtr, this);
  }

  public float getDampingOrthoLin() {
    return DynamicsJNI.btSliderConstraint_getDampingOrthoLin(swigCPtr, this);
  }

  public float getSoftnessOrthoAng() {
    return DynamicsJNI.btSliderConstraint_getSoftnessOrthoAng(swigCPtr, this);
  }

  public float getRestitutionOrthoAng() {
    return DynamicsJNI.btSliderConstraint_getRestitutionOrthoAng(swigCPtr, this);
  }

  public float getDampingOrthoAng() {
    return DynamicsJNI.btSliderConstraint_getDampingOrthoAng(swigCPtr, this);
  }

  public void setSoftnessDirLin(float softnessDirLin) {
    DynamicsJNI.btSliderConstraint_setSoftnessDirLin(swigCPtr, this, softnessDirLin);
  }

  public void setRestitutionDirLin(float restitutionDirLin) {
    DynamicsJNI.btSliderConstraint_setRestitutionDirLin(swigCPtr, this, restitutionDirLin);
  }

  public void setDampingDirLin(float dampingDirLin) {
    DynamicsJNI.btSliderConstraint_setDampingDirLin(swigCPtr, this, dampingDirLin);
  }

  public void setSoftnessDirAng(float softnessDirAng) {
    DynamicsJNI.btSliderConstraint_setSoftnessDirAng(swigCPtr, this, softnessDirAng);
  }

  public void setRestitutionDirAng(float restitutionDirAng) {
    DynamicsJNI.btSliderConstraint_setRestitutionDirAng(swigCPtr, this, restitutionDirAng);
  }

  public void setDampingDirAng(float dampingDirAng) {
    DynamicsJNI.btSliderConstraint_setDampingDirAng(swigCPtr, this, dampingDirAng);
  }

  public void setSoftnessLimLin(float softnessLimLin) {
    DynamicsJNI.btSliderConstraint_setSoftnessLimLin(swigCPtr, this, softnessLimLin);
  }

  public void setRestitutionLimLin(float restitutionLimLin) {
    DynamicsJNI.btSliderConstraint_setRestitutionLimLin(swigCPtr, this, restitutionLimLin);
  }

  public void setDampingLimLin(float dampingLimLin) {
    DynamicsJNI.btSliderConstraint_setDampingLimLin(swigCPtr, this, dampingLimLin);
  }

  public void setSoftnessLimAng(float softnessLimAng) {
    DynamicsJNI.btSliderConstraint_setSoftnessLimAng(swigCPtr, this, softnessLimAng);
  }

  public void setRestitutionLimAng(float restitutionLimAng) {
    DynamicsJNI.btSliderConstraint_setRestitutionLimAng(swigCPtr, this, restitutionLimAng);
  }

  public void setDampingLimAng(float dampingLimAng) {
    DynamicsJNI.btSliderConstraint_setDampingLimAng(swigCPtr, this, dampingLimAng);
  }

  public void setSoftnessOrthoLin(float softnessOrthoLin) {
    DynamicsJNI.btSliderConstraint_setSoftnessOrthoLin(swigCPtr, this, softnessOrthoLin);
  }

  public void setRestitutionOrthoLin(float restitutionOrthoLin) {
    DynamicsJNI.btSliderConstraint_setRestitutionOrthoLin(swigCPtr, this, restitutionOrthoLin);
  }

  public void setDampingOrthoLin(float dampingOrthoLin) {
    DynamicsJNI.btSliderConstraint_setDampingOrthoLin(swigCPtr, this, dampingOrthoLin);
  }

  public void setSoftnessOrthoAng(float softnessOrthoAng) {
    DynamicsJNI.btSliderConstraint_setSoftnessOrthoAng(swigCPtr, this, softnessOrthoAng);
  }

  public void setRestitutionOrthoAng(float restitutionOrthoAng) {
    DynamicsJNI.btSliderConstraint_setRestitutionOrthoAng(swigCPtr, this, restitutionOrthoAng);
  }

  public void setDampingOrthoAng(float dampingOrthoAng) {
    DynamicsJNI.btSliderConstraint_setDampingOrthoAng(swigCPtr, this, dampingOrthoAng);
  }

  public void setPoweredLinMotor(boolean onOff) {
    DynamicsJNI.btSliderConstraint_setPoweredLinMotor(swigCPtr, this, onOff);
  }

  public boolean getPoweredLinMotor() {
    return DynamicsJNI.btSliderConstraint_getPoweredLinMotor(swigCPtr, this);
  }

  public void setTargetLinMotorVelocity(float targetLinMotorVelocity) {
    DynamicsJNI.btSliderConstraint_setTargetLinMotorVelocity(swigCPtr, this, targetLinMotorVelocity);
  }

  public float getTargetLinMotorVelocity() {
    return DynamicsJNI.btSliderConstraint_getTargetLinMotorVelocity(swigCPtr, this);
  }

  public void setMaxLinMotorForce(float maxLinMotorForce) {
    DynamicsJNI.btSliderConstraint_setMaxLinMotorForce(swigCPtr, this, maxLinMotorForce);
  }

  public float getMaxLinMotorForce() {
    return DynamicsJNI.btSliderConstraint_getMaxLinMotorForce(swigCPtr, this);
  }

  public void setPoweredAngMotor(boolean onOff) {
    DynamicsJNI.btSliderConstraint_setPoweredAngMotor(swigCPtr, this, onOff);
  }

  public boolean getPoweredAngMotor() {
    return DynamicsJNI.btSliderConstraint_getPoweredAngMotor(swigCPtr, this);
  }

  public void setTargetAngMotorVelocity(float targetAngMotorVelocity) {
    DynamicsJNI.btSliderConstraint_setTargetAngMotorVelocity(swigCPtr, this, targetAngMotorVelocity);
  }

  public float getTargetAngMotorVelocity() {
    return DynamicsJNI.btSliderConstraint_getTargetAngMotorVelocity(swigCPtr, this);
  }

  public void setMaxAngMotorForce(float maxAngMotorForce) {
    DynamicsJNI.btSliderConstraint_setMaxAngMotorForce(swigCPtr, this, maxAngMotorForce);
  }

  public float getMaxAngMotorForce() {
    return DynamicsJNI.btSliderConstraint_getMaxAngMotorForce(swigCPtr, this);
  }

  public float getLinearPos() {
    return DynamicsJNI.btSliderConstraint_getLinearPos(swigCPtr, this);
  }

  public float getAngularPos() {
    return DynamicsJNI.btSliderConstraint_getAngularPos(swigCPtr, this);
  }

  public boolean getSolveLinLimit() {
    return DynamicsJNI.btSliderConstraint_getSolveLinLimit(swigCPtr, this);
  }

  public float getLinDepth() {
    return DynamicsJNI.btSliderConstraint_getLinDepth(swigCPtr, this);
  }

  public boolean getSolveAngLimit() {
    return DynamicsJNI.btSliderConstraint_getSolveAngLimit(swigCPtr, this);
  }

  public float getAngDepth() {
    return DynamicsJNI.btSliderConstraint_getAngDepth(swigCPtr, this);
  }

  public void calculateTransforms(Matrix4 transA, Matrix4 transB) {
    DynamicsJNI.btSliderConstraint_calculateTransforms(swigCPtr, this, transA, transB);
  }

  public void testLinLimits() {
    DynamicsJNI.btSliderConstraint_testLinLimits(swigCPtr, this);
  }

  public void testAngLimits() {
    DynamicsJNI.btSliderConstraint_testAngLimits(swigCPtr, this);
  }

  public Vector3 getAncorInA() {
	return DynamicsJNI.btSliderConstraint_getAncorInA(swigCPtr, this);
}

  public Vector3 getAncorInB() {
	return DynamicsJNI.btSliderConstraint_getAncorInB(swigCPtr, this);
}

  public boolean getUseFrameOffset() {
    return DynamicsJNI.btSliderConstraint_getUseFrameOffset(swigCPtr, this);
  }

  public void setUseFrameOffset(boolean frameOffsetOnOff) {
    DynamicsJNI.btSliderConstraint_setUseFrameOffset(swigCPtr, this, frameOffsetOnOff);
  }

  public void setFrames(Matrix4 frameA, Matrix4 frameB) {
    DynamicsJNI.btSliderConstraint_setFrames(swigCPtr, this, frameA, frameB);
  }

  public void setParam(int num, float value, int axis) {
    DynamicsJNI.btSliderConstraint_setParam__SWIG_0(swigCPtr, this, num, value, axis);
  }

  public void setParam(int num, float value) {
    DynamicsJNI.btSliderConstraint_setParam__SWIG_1(swigCPtr, this, num, value);
  }

  public float getParam(int num, int axis) {
    return DynamicsJNI.btSliderConstraint_getParam__SWIG_0(swigCPtr, this, num, axis);
  }

  public float getParam(int num) {
    return DynamicsJNI.btSliderConstraint_getParam__SWIG_1(swigCPtr, this, num);
  }

}
