package com.bigbratan.emulair.common.managers.storage.scanner

import com.bigbratan.emulair.common.metadata.retrograde.SystemID

class MagicNumber(val offset: Int, val numbers: ByteArray, val systemID: SystemID)
