package org.sesac.management.util.common

import android.view.LayoutInflater
import android.view.ViewGroup

typealias FragmentInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
