package ambl.logic.lang.functs;

public enum MathFuncts {
    // arithmetic
    add, sub,
    mult, div,

    power, root, sqrt, cbrt,
    
    fact,

    intdiv, mod, posmod,

    abs, floor, ceil, round,

    // bitwise
    bshiftr, bshiftl,

    band, bnand, bxor, bxnor, bor, bnor, bnot,

    // logical
    and, or, xor, not,

    // comparison
    equal, strictequal, smaller, greater, smallerequal, greaterequal,

    // extra
    nget, ngetrad, nshiftr, nshiftl, max, min, noise, rand, randint, numer, denom, sign, clamp, fract, wrap,

    // trigonometry + geometry
    atan, atan2, acos, asin, cos, sin, csc, csch, sec, sech, cot, coth, acsc, asec, acot, acsch, asech, shortangdist, cosh, sinh, tanh, acosh, asinh, atanh,
    degtorad, radtodeg, gradtodeg, degtograd, gradtorad, radtograd,

    // algebra
    log, natlog, log10, log2, exp, expm1, log1p, 

    // combinatorics
    permutation, combination,
    gcd, lcm,
    isprime, factorcount, nthfactor, coprime

    // interpolations
    lerp,
    clerp,
    remap,
    step,
    sstep,
    serstep,
    ilerp,
    easein,
    easeout,
    easeinout,
    bilerp,
    trilerp,
    slerp,
    alerp
}