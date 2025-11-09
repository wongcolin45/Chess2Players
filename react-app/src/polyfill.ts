// Fix SockJS expecting `global` in browser
// eslint-disable-next-line @typescript-eslint/no-explicit-any
(window as any).global ??= window;