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

    teta = {"NVB": [-55, -25, -40],
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

    force = {'PVB+NB': 'P', 'PVB+N': 'PB', 'PVB+ZO': 'PVB', 'PVB+P': 'PVVB', 'PVB+PB': 'PVVB',
             'PB+NB': 'Z', 'PB+N': 'P', 'PB+ZO': 'PB', 'PB+P': 'PVB', 'PB+PB': 'PVVB',
             'P+NB': 'N', 'P+N': 'Z', 'P+ZO': 'P', 'P+P': 'PB', 'P+PB': 'PVB',
             'ZO+NB': 'NB', 'ZO+N': 'N', 'ZO+ZO': 'Z', 'ZO+P': 'P', 'ZO+PB': 'PB',
             'N+NB': 'NVB', 'N+N': 'NB', 'N+ZO': 'N', 'N+P': 'Z', 'N+PB': 'P',
             'NB+NB': 'NVVB', 'NB+N': 'NVB', 'NB+ZO': 'NB', 'NB+P': 'N', 'NB+PB': 'Z',
             'NVB+NB': 'NVVB', 'NVB+N': 'NVVB', 'NVB+ZO': 'NVB', 'NVB+P': 'NB', 'NVB+PB': 'N',
             }

    products = {'NVVB': -32,
                'NVB': -24,
                'NB': -16,
                'N': -8,
                'Z': 0,
                'P': 8,
                'PB': 16,
                'PVB': 24,
                'PVVB': 32}

    niu = collections.defaultdict(dict)

    for t_key in teta:
        for o_key in omega:
            niu[t_key][o_key] = 0

    for t_key in teta:
        for o_key in omega:
            if omega[o_key][0] <= w <= omega[o_key][1]:
                niu[t_key][o_key] = min((w - omega[o_key][0]) / (omega[o_key][2] - omega[o_key][0]), 1,
                                        (omega[o_key][1] - w) / (omega[o_key][1] - omega[o_key][2]))
        if teta[t_key][0] <= t <= teta[t_key][1]:
            niu[t_key] = min((t - teta[t_key][0]) / (teta[t_key][2] - teta[t_key][0]), 1,
                             (teta[t_key][1] - t) / (teta[t_key][1] - teta[t_key][2]))

    print(niu)

    forces = {}
    for key in teta:
        for key_2 in omega:
            print(key, " -> ", key_2)
            val = min(niu[key][key_2])
            new_key = key + '+' + key_2
            if force[new_key] not in forces:
                forces[force[new_key]] = val
            elif forces[force[new_key]] < val:
                forces[force[new_key]] = val

    # print(forces)

    sum = 0
    product = 0
    for key in forces:
        sum += forces[key]
        product += forces[key] * products[key]
    try:
        f = product / sum
    except:
        f = 0
    return f
