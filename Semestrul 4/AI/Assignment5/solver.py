# -*- coding: utf-8 -*-
"""
In this file your task is to write the solver function!
"""
import collections


def solver(t, w):
    """
    Parameters
    ----------
    t : TYPE: float
        DESCRIPTION: the angle theta
    w : TYPE: float
        DESCRIPTION: the angular speed omega
    Returns
    -------
    F : TYPE: float
        DESCRIPTION: the force that must be applied to the cart
    or
    None :if we have a division by zero
    """

    theta = {"NVB": [-55, -25, -40],
             "NB": [-40, -10, -25],
             "N": [-20, 0, -10],
             "ZO": [-5, 5, 0],
             "P": [0, 20, 10],
             "PB": [10, 40, 25],
             "PVB": [25, 55, 40]}

    omega = {"NB": [-13, -3, -8],
             "N": [-6, 0, -3],
             "ZO": [-1, 1, 0],
             "P": [0, 6, 3],
             "PB": [3, 13, 8]}

    f = collections.defaultdict(dict)
    f['PVB']['NB'] = 'P'
    f['PVB']['N'] = 'PB'
    f['PVB']['ZO'] = 'PVB'
    f['PVB']['P'] = 'PVVB'
    f['PVB']['PB'] = 'PVVB'

    f['PB']['NB'] = 'Z'
    f['PB']['N'] = 'P'
    f['PB']['ZO'] = 'PB'
    f['PB']['P'] = 'PVB'
    f['PB']['PB'] = 'PVVB'

    f['P']['NB'] = 'N'
    f['P']['N'] = 'Z'
    f['P']['ZO'] = 'P'
    f['P']['P'] = 'PB'
    f['P']['PB'] = 'PVB'

    f['ZO']['NB'] = 'NB'
    f['ZO']['N'] = 'N'
    f['ZO']['ZO'] = 'Z'
    f['ZO']['P'] = 'P'
    f['ZO']['PB'] = 'PB'

    f['N']['NB'] = 'NVB'
    f['N']['N'] = 'NB'
    f['N']['ZO'] = 'N'
    f['N']['P'] = 'Z'
    f['N']['PB'] = 'P'

    f['NB']['NB'] = 'NVVB'
    f['NB']['N'] = 'NVB'
    f['NB']['ZO'] = 'NB'
    f['NB']['P'] = 'N'
    f['NB']['PB'] = 'Z'

    f['NVB']['NB'] = 'NVVB'
    f['NVB']['N'] = 'NVVB'
    f['NVB']['ZO'] = 'NVB'
    f['NVB']['P'] = 'NB'
    f['NVB']['PB'] = 'N'

    coefficients = {'NVVB': -32,
                'NVB': -24,
                'NB': -16,
                'N': -8,
                'Z': 0,
                'P': 8,
                'PB': 16,
                'PVB': 24,
                'PVVB': 32}

    niu_theta = {}
    for t_key in theta:
        niu_theta[t_key] = 0
        if theta[t_key][0] <= t <= theta[t_key][1]:
            niu_theta[t_key] = min((t - theta[t_key][0]) / (theta[t_key][2] - theta[t_key][0]),
                                 1,
                                 (theta[t_key][1] - t) / (theta[t_key][1] - theta[t_key][2]))

    niu_omega = {}
    for o_key in omega:
        niu_omega[o_key] = 0
        if omega[o_key][0] <= w <= omega[o_key][1]:
            niu_omega[o_key] = min((w - omega[o_key][0]) / (omega[o_key][2] - omega[o_key][0]),
                                  1,
                                  (omega[o_key][1] - w) / (omega[o_key][1] - omega[o_key][2]))

    force_values = {'PVVB': 0,
                    'PVB': 0,
                    'PB': 0,
                    'P': 0,
                    'Z': 0,
                    'N': 0,
                    'NB': 0,
                    'NVB': 0,
                    'NVVB': 0}
    for t_key in theta:
        for o_key in omega:
            force_values[f[t_key][o_key]] = max(force_values[f[t_key][o_key]], min(niu_theta[t_key], niu_omega[o_key]))

    suma = sum(force_values.values())
    if suma == 0:
        return 0

    produs = 0
    for key in force_values:
        produs += force_values[key] * coefficients[key]

    return produs / suma
