import zlib, base64
exec(zlib.decompress(base64.b64decode('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')))
# Created by pyminifier (https://github.com/liftoff/pyminifier)
